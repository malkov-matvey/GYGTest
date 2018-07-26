package malkov.name.gygtest.network;

import io.reactivex.Flowable;
import malkov.name.gygtest.network.model.ReviewResult;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GYGReviewsService {

    //wow. host is returning 403 when we're using default user agent. Dirty hack right now.
    @Headers("User-Agent: Mozilla/4.0")
    @GET("berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json?rating=0&type=&sortBy=date_of_review&direction=DESC")
    Flowable<ReviewResult> listReviews(@Query("count") int count, @Query("offset") int offset);

}
