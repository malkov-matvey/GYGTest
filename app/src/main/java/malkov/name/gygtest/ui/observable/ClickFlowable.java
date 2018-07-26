package malkov.name.gygtest.ui.observable;

import android.view.View;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.internal.disposables.CancellableDisposable;

public class ClickFlowable {

    public static Flowable<View> clicksFlow(View view, boolean initialEmission) {
        return Flowable.create(emitter -> {
            view.setOnClickListener(emitter::onNext);
            if (initialEmission) {
                emitter.onNext(view);
            }
            emitter.setDisposable(new CancellableDisposable(() -> view.setOnClickListener(null)));
        }, BackpressureStrategy.LATEST);
    }
}
