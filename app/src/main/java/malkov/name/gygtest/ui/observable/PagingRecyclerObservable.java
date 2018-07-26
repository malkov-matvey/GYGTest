package malkov.name.gygtest.ui.observable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;

public class PagingRecyclerObservable {

    //threshold shows in what percent of item whe should pass before loading new
    //[0 .. 1]
    public static Observable<Integer> paging(final RecyclerView rv, final float threshold) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
                final RecyclerView.LayoutManager lm = rv.getLayoutManager();
                if (!(lm instanceof LinearLayoutManager)) {
                    throw new IllegalStateException("only LinearLayoutManagers supported by PagingObs");
                }
                final LinearLayoutManager llm = (LinearLayoutManager) lm;

                final RecyclerView.OnScrollListener sl = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        if (recyclerView.getAdapter().getItemCount() * threshold < llm.findLastVisibleItemPosition()) {
                            emitter.onNext(recyclerView.getAdapter().getItemCount());
                        }
                    }
                };
                rv.addOnScrollListener(sl);
                emitter.setDisposable(new CancellableDisposable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        rv.removeOnScrollListener(sl);
                    }
                }));
            }
        }).distinctUntilChanged();
    }
}
