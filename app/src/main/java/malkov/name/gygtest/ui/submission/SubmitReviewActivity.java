package malkov.name.gygtest.ui.submission;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import malkov.name.gygtest.R;

public class SubmitReviewActivity extends AppCompatActivity {

    private final CompositeDisposable disposable = new CompositeDisposable();
    Button submit;
    EditText title;
    EditText message;
    RatingBar rating;
    private SubmitReviewViewModel vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_review);

        vm = ViewModelProviders.of(this).get(SubmitReviewViewModel.class);

        title = findViewById(R.id.editTitle);
        message = findViewById(R.id.messageEdit);
        rating = findViewById(R.id.rate_input);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(v ->
                submitReview(title.getText(), message.getText(), rating.getRating())
        );
    }

    private void submitReview(CharSequence title, CharSequence message, float rating) {
        if (rating != 0.0f && (!TextUtils.isEmpty(title) || TextUtils.isEmpty(message))) {
            disposable.add(vm.submitReview(title.toString(), message.toString(), rating)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> setUiEnabled(false))
                    .subscribe(res -> {
                        if (res) {
                            finish();
                        } else {
                            setUiEnabled(true);
                            Snackbar.make(submit, R.string.general_network_error, Snackbar.LENGTH_LONG).show();
                        }
                    }));
        } else {
            (new AlertDialog.Builder(this)).setTitle(R.string.submission_no_data_title)
                    .setMessage(R.string.submission_no_data_message)
                    .setNeutralButton(android.R.string.ok, (dialog, which) -> { }).show();
        }
    }

    private void setUiEnabled(boolean enabled) {
        submit.setEnabled(enabled);
        title.setEnabled(enabled);
        message.setEnabled(enabled);
        rating.setEnabled(enabled);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        disposable.clear();
    }
}
