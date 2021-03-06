package malkov.name.gygtest.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import malkov.name.gygtest.db.ReviewsRepo;
import malkov.name.gygtest.db.model.Review;
import malkov.name.gygtest.network.Converter;
import malkov.name.gygtest.network.ReviewNetworkServicesProvider;
import malkov.name.gygtest.network.model.ReviewResult;
import malkov.name.gygtest.utils.Utils;

public class ReviewsViewModel extends AndroidViewModel {

    private static int DEFAULT_COUNT = 20;

    private ReviewsRepo repo;
    private Disposable retrieveDisposable;

    public ReviewsViewModel(@NonNull Application application) {
        super(application);
        init(application.getApplicationContext());
    }

    private void init(final Context context) {
        repo = new ReviewsRepo(context);
    }

    private Flowable<List<Review>> fetchFromNetwork(Integer offset) {
        return ReviewNetworkServicesProvider.buildService()
                .listReviews(DEFAULT_COUNT, offset)
                .subscribeOn(Schedulers.io())
                .onErrorReturn(throwable -> new ReviewResult(-1, Collections.emptyList()))
                .map(rs -> Converter.convertReviews(rs.getResultList()))
                .doOnNext(rs -> repo.insertBatch(rs));
    }

    public Flowable<List<Review>> bind(Flowable<Integer> paging, Flowable<?> retrySignals) {
        Flowable<Integer> withRetry =
                Flowable.combineLatest(paging.startWith(0), retrySignals, (i, retry) -> i);
        final Flowable<List<Review>> reviewObs = withRetry.observeOn(Schedulers.io())
                .flatMap(offset -> repo.loadReviewsWithOffset(offset).flatMap(reviews -> {
                    if (reviews.size() < DEFAULT_COUNT) {
                        return fetchFromNetwork(offset + reviews.size())
                                .map(rs -> Utils.mergeLists(reviews, rs));
                    } else {
                        return Flowable.just(reviews);
                    }
                }));
        return reviewObs.scan(Utils::mergeLists);
    }

    @Override
    protected void onCleared() {
        if (retrieveDisposable != null && !retrieveDisposable.isDisposed()) {
            retrieveDisposable.dispose();
            retrieveDisposable = null;
        }
    }
}
