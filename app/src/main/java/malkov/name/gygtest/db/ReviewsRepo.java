package malkov.name.gygtest.db;

import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import malkov.name.gygtest.db.model.Review;

public class ReviewsRepo {

    private final ReviewDao reviews;

    public ReviewsRepo(final Context context) {
         reviews = MainDatabase.getInstance(context.getApplicationContext()).reviews();
    }

    public void insert(final Review r) {
        reviews.insert(r);
    }

    public void insertBatch(final List<Review> rs) {
        reviews.insertAll(rs);
    }

    public Flowable<List<Review>> loadReviewsWithOffset(int offset) {
        return reviews.loadReviewsWithOffset(offset).toFlowable();
    }
}
