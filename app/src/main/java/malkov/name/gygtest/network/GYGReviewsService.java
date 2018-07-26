package malkov.name.gygtest.network;

import io.reactivex.Flowable;
import io.reactivex.Single;
import malkov.name.gygtest.network.model.ReviewResult;
import malkov.name.gygtest.network.model.ReviewSubmission;
import malkov.name.gygtest.network.model.ReviewSubmissionResult;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GYGReviewsService {

    //wow. host is returning 403 when we're using default user agent. Dirty hack right now.
    @Headers("User-Agent: Mozilla/4.0")
    @GET("berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json?rating=0&type=&sortBy=date_of_review&direction=DESC")
    Flowable<ReviewResult> listReviews(@Query("count") int count, @Query("offset") int offset);

    /**
     * So, this is our method to post and submit reviews from the user. Using same retrofit adapter is pretty fine.
     * Communicating using JSON which is serialized with Gson as in GET method.
     * @param submission wrapper for user's entered data + auth key to allow server to authenticate user.
     *                   this allows server to insert author name and language and country by himself
     *                   we're just passing what users entered.
     * @return Returning Result which contains ReviewFromNetwork entity to add to database later + response code
     */
    @POST("berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776")
    Single<ReviewSubmissionResult> submitReview(@Body ReviewSubmission submission);

}
