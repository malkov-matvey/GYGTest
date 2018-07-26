package malkov.name.gygtest.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import malkov.name.gygtest.R;
import malkov.name.gygtest.ui.observable.ClickFlowable;
import malkov.name.gygtest.ui.observable.PagingRecyclerFlowable;
import malkov.name.gygtest.ui.submission.SubmitReviewActivity;

public class MainActivity extends AppCompatActivity {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private ReviewsAdapter adapter;
    private RecyclerView list;
    private View retry;
    private View progress;
    private ReviewsViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        retry = findViewById(R.id.retry_section);
        progress = findViewById(R.id.progress);
        adapter = new ReviewsAdapter();
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        findViewById(R.id.fab).setOnClickListener(v -> startActivity(new Intent(this, SubmitReviewActivity.class)));

        vm = ViewModelProviders.of(this).get(ReviewsViewModel.class);
        final Flowable<Integer> paging = PagingRecyclerFlowable.paging(list, 0.75f);
        final Flowable<View> retrySignal = ClickFlowable.clicksFlow(retry, true)
                .doOnNext(res -> stateLoading());
        startFlow(paging, retrySignal);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanup();
    }

    private void cleanup() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
            disposable.clear();
        }
    }

    private void startFlow(Flowable<Integer> paging, Flowable<View> retrySignal) {
        disposable.add(vm.bind(paging, retrySignal)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> stateLoading())
                .subscribe(reviews -> {
                    if (reviews.isEmpty()) {
                        stateRetry();
                    } else {
                        stateSuccess();
                        adapter.setData(reviews);
                        adapter.notifyDataSetChanged();
                    }
                }));
    }

    private void stateLoading() {
        list.setVisibility(View.GONE);
        retry.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    private void stateRetry() {
        progress.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
        retry.setVisibility(View.VISIBLE);
    }

    private void stateSuccess() {
        list.setVisibility(View.VISIBLE);
        retry.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
    }
}
