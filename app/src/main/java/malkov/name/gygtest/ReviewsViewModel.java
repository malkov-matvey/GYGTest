package malkov.name.gygtest;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import malkov.name.gygtest.db.ReviewsRepo;
import malkov.name.gygtest.db.model.Review;
import malkov.name.gygtest.network.ReviewNetworkServicesProvider;
import malkov.name.gygtest.network.model.ReviewFromNetwork;
import malkov.name.gygtest.network.model.ReviewResult;

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

    private float convertRatingSafe(@NonNull final String rating) {
        float res = 0;
        try {
            res = Float.parseFloat(rating);
        } catch (Throwable t) {
            res = 0;
        }
        return res;
    }

    public List<Review> convertReviews(List<ReviewFromNetwork> rs) {
        final List<Review> res = new ArrayList<>(rs.size());
        for (final ReviewFromNetwork r : rs) {

            res.add(new Review(
                    r.getId(),
                    convertRatingSafe(r.getRating()),
                    r.getTitle(),
                    r.getMessage(),
                    r.getAuthor(),
                    r.getDateStr(),
                    r.isForeignLanguage(),
                    r.getLanguageIcoCode(),
                    r.getTravelerType(),
                    r.getReviewerName(),
                    r.getReviewerCountry()
            ));
        }
        return Collections.unmodifiableList(res);
    }

    @SuppressLint("CheckResult")
    public Flowable<List<Review>> bind(Observable<Long> paging) {
        paging.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e("NEW NEW", aLong.toString());
            }
        });
        retrieveDisposable = ReviewNetworkServicesProvider.buildService()
                .listReviews(DEFAULT_COUNT, 1)
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Function<Throwable, ReviewResult>() {
                    @Override
                    public ReviewResult apply(Throwable throwable) throws Exception {
                        return new ReviewResult(-1, Collections.<ReviewFromNetwork>emptyList());
                    }
                })
                .subscribe(new Consumer<ReviewResult>() {
                    @Override
                    public void accept(ReviewResult res) throws Exception {
                        repo.insertBatch(convertReviews(res.getResultList()));
                    }
                });
        return repo.loadReviews();
    }

    @Override
    protected void onCleared() {
        if (retrieveDisposable != null && !retrieveDisposable.isDisposed()) {
            retrieveDisposable.dispose();
            retrieveDisposable = null;
        }
    }
}
