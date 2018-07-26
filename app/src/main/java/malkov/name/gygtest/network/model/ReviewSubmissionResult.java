package malkov.name.gygtest.network.model;

import com.google.gson.annotations.SerializedName;

public class ReviewSubmissionResult {

    @SerializedName("code")
    private int networkCode;

    @SerializedName("review")
    private ReviewFromNetwork review;

    public ReviewSubmissionResult(int networkCode, ReviewFromNetwork review) {
        this.networkCode = networkCode;
        this.review = review;
    }

    public int getNetworkCode() {
        return networkCode;
    }

    public ReviewFromNetwork getReview() {
        return review;
    }
}
