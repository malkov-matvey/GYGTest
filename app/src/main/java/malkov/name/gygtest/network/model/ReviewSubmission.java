package malkov.name.gygtest.network.model;

import com.google.gson.annotations.SerializedName;

public class ReviewSubmission {

    @SerializedName("auth_key")
    private String authKey;

    @SerializedName("title")
    private String title;

    @SerializedName("message")
    private String message;

    @SerializedName("rating")
    private float rating;

    public ReviewSubmission(String authKey, String title, String message, float rating) {
        this.authKey = authKey;
        this.title = title;
        this.message = message;
        this.rating = rating;
    }
}
