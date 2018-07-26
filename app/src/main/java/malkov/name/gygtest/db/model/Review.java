package malkov.name.gygtest.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "reviews")
public class Review {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private long id;

    @NonNull
    @ColumnInfo(name = "rating")
    private float rating;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "message")
    private String message;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "date")
    private String prettyDate;

    @ColumnInfo(name = "foreignLanguage")
    private boolean isForeignLanguage;

    @ColumnInfo(name = "language_ico")
    private String languageIco;

    @ColumnInfo(name = "traveler_type")
    private String travelerType;

    @ColumnInfo(name = "reviewer_name")
    private String reviewerName;

    @ColumnInfo(name = "reviewer_country")
    private String reviewerCountry;

    public Review(@NonNull long id,
                  @NonNull float rating,
                  String title,
                  String message,
                  String author,
                  String prettyDate,
                  boolean isForeignLanguage,
                  String languageIco,
                  String travelerType,
                  String reviewerName,
                  String reviewerCountry) {
        this.id = id;
        this.rating = rating;
        this.title = title;
        this.message = message;
        this.author = author;
        this.prettyDate = prettyDate;
        this.isForeignLanguage = isForeignLanguage;
        this.languageIco = languageIco;
        this.travelerType = travelerType;
        this.reviewerName = reviewerName;
        this.reviewerCountry = reviewerCountry;
    }

    @NonNull
    public long getId() {
        return id;
    }

    @NonNull
    public float getRating() {
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

    public String getPrettyDate() {
        return prettyDate;
    }

    public boolean isForeignLanguage() {
        return isForeignLanguage;
    }

    public String getLanguageIco() {
        return languageIco;
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
