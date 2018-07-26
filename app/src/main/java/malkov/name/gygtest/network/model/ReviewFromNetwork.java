package malkov.name.gygtest.network.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


public class ReviewFromNetwork {

    @SerializedName("review_id")
    private long id;

    @NonNull
    @SerializedName("rating")
    private String rating;

    @NonNull
    @SerializedName("title")
    private String title;

    @NonNull
    @SerializedName("message")
    private String message;

    @NonNull
    @SerializedName("author")
    private String author;

    @SerializedName("foreignLanguage")
    private boolean isForeignLanguage;

    @NonNull
    @SerializedName("date")
    private String dateStr;

    @NonNull
    @SerializedName("languageCode")
    private String languageIcoCode;

    @NonNull
    @SerializedName("traveler_type")
    private String travelerType;

    @NonNull
    @SerializedName("reviewerName")
    private String reviewerName;

    @NonNull
    @SerializedName("reviewerCountry")
    private String reviewerCountry;

    public ReviewFromNetwork(long id, String rating, String title, String message, String author, boolean isForeignLanguage, String dateStr, String languageIcoCode, String travelerType, String reviewerName, String reviewerCountry) {
        this.id = id;
        this.rating = rating;
        this.title = title;
        this.message = message;
        this.author = author;
        this.isForeignLanguage = isForeignLanguage;
        this.dateStr = dateStr;
        this.languageIcoCode = languageIcoCode;
        this.travelerType = travelerType;
        this.reviewerName = reviewerName;
        this.reviewerCountry = reviewerCountry;
    }

    public long getId() {
        return id;
    }

    public String getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isForeignLanguage() {
        return isForeignLanguage;
    }

    public String getDateStr() {
        return dateStr;
    }

    public String getLanguageIcoCode() {
        return languageIcoCode;
    }

    public String getTravelerType() {
        return travelerType;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getReviewerCountry() {
        return reviewerCountry;
    }
}
