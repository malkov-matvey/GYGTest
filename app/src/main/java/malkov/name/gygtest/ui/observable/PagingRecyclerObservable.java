package malkov.name.gygtest.ui.observable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.internal.disposables.CancellableDisposable;

public class PagingRecyclerObservable {

    //threshold shows in what percent of item whe should pass before loading new
    //[0 .. 1]
    public static Flowable<Integer> paging(final RecyclerView rv, final float threshold) {
        return Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
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
            emitter.setDisposable(new CancellableDisposable(() -> rv.removeOnScrollListener(sl)));
        }, BackpressureStrategy.BUFFER).distinctUntilChanged();
    }
}
