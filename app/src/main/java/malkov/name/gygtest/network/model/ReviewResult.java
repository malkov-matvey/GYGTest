package malkov.name.gygtest.network.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResult {

    @SerializedName("total_reviews_comments")
    private int totalReviewsCount;

    @NonNull
    @SerializedName("data")
    private List<ReviewFromNetwork> resultList;

    public ReviewResult(int totalReviewsCount, List<ReviewFromNetwork> resultList) {
        this.totalReviewsCount = totalReviewsCount;
        this.resultList = resultList;
    }

    public int getTotalReviewsCount() {
        return totalReviewsCount;
    }

    public List<ReviewFromNetwork> getResultList() {
        return resultList;
    }
}
