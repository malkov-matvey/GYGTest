package malkov.name.gygtest.network;

import java.util.List;

import io.reactivex.Observable;
import malkov.name.gygtest.network.model.ReviewFromNetwork;
import malkov.name.gygtest.network.model.ReviewResult;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GYGReviewsService {

    @Headers("User-Agent: Mozilla/4.0")
    @GET("berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json?rating=0&type=&sortBy=date_of_review&direction=DESC")
    Observable<ReviewResult> listReviews(@Query("count") int count, @Query("page") int pageNumber);

}
