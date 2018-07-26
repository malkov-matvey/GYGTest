package malkov.name.gygtest.ui.submission;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.Single;
import malkov.name.gygtest.auth.AuthRepo;
import malkov.name.gygtest.db.ReviewsRepo;
import malkov.name.gygtest.network.Converter;
import malkov.name.gygtest.network.ReviewNetworkServicesProvider;
import malkov.name.gygtest.network.model.ReviewSubmission;
import malkov.name.gygtest.network.model.ReviewSubmissionResult;
import malkov.name.gygtest.utils.Constants;

public class SubmitReviewViewModel extends AndroidViewModel {

    private ReviewsRepo repo;
    private AuthRepo authRepo;

    public SubmitReviewViewModel(@NonNull Application application) {
        super(application);
        init(application.getApplicationContext());
    }

    private void init(final Context context) {
        repo = new ReviewsRepo(context);
        authRepo = new AuthRepo();
    }

    public Single<Boolean> submitReview(String title, String message, float rating) {
        final String authKey = authRepo.authKey(getApplication().getApplicationContext());
        ReviewSubmission submission = new ReviewSubmission(authKey, title, message, rating);
        return ReviewNetworkServicesProvider.buildService().submitReview(submission)
                .onErrorReturn(thr -> new ReviewSubmissionResult(Constants.NETWORK_CODE_INTERNAL_ERROR, null))
                .doOnSuccess(res -> {
                    if (res.getNetworkCode() == Constants.NETWORK_CODE_OK) {
                        repo.insert(Converter.convertReview(res.getReview()));
                    }
                }).map(res -> res.getNetworkCode() == Constants.NETWORK_CODE_OK);
    }
}
