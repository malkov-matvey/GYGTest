package malkov.name.gygtest;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import malkov.name.gygtest.ui.list.ReviewsAdapter;
import malkov.name.gygtest.ui.observable.PagingRecyclerObservable;

public class MainActivity extends AppCompatActivity {

    private RecyclerView list;
    private ReviewsViewModel vm;
    private ReviewsAdapter adapter;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        final View progress = findViewById(R.id.progress);
        vm = ViewModelProviders.of(this).get(ReviewsViewModel.class);
        adapter = new ReviewsAdapter(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        list.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        Flowable<Integer> paging = PagingRecyclerObservable.paging(list, 0.75f);
        disposable = vm.bind(paging).observeOn(AndroidSchedulers.mainThread()).subscribe(reviews -> {
            adapter.setData(reviews);
            adapter.notifyDataSetChanged();
            if (!reviews.isEmpty()) {
                list.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }
}
