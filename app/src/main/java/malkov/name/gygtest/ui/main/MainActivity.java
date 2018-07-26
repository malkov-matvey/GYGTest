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
import malkov.name.gygtest.ui.submission.SubmitReviewActivity;
import malkov.name.gygtest.ui.observable.PagingRecyclerObservable;

public class MainActivity extends AppCompatActivity {

    private RecyclerView list;
    private ReviewsViewModel vm;
    private ReviewsAdapter adapter;
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        final View progress = findViewById(R.id.progress);
        findViewById(R.id.fab).setOnClickListener(v -> startActivity(new Intent(this, SubmitReviewActivity.class)));
        adapter = new ReviewsAdapter(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        list.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        vm = ViewModelProviders.of(this).get(ReviewsViewModel.class);
        Flowable<Integer> paging = PagingRecyclerObservable.paging(list, 0.75f);
        disposable.add(vm.bind(paging).observeOn(AndroidSchedulers.mainThread()).subscribe(reviews -> {
            adapter.setData(reviews);
            adapter.notifyDataSetChanged();
            if (!reviews.isEmpty()) {
                list.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
            disposable.clear();
        }
    }
}
