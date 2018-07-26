package malkov.name.gygtest;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import malkov.name.gygtest.db.model.Review;
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
        vm = ViewModelProviders.of(this).get(ReviewsViewModel.class);
        adapter = new ReviewsAdapter(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        Observable<Long> paging = PagingRecyclerObservable.paging(list, 0.75f);
        disposable = vm.bind(paging).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Review>>() {
            @Override
            public void accept(List<Review> reviews) throws Exception {
                adapter.setData(reviews);
                adapter.notifyDataSetChanged();
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
