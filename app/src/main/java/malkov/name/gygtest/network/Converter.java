package malkov.name.gygtest.network;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import malkov.name.gygtest.db.model.Review;
import malkov.name.gygtest.network.model.ReviewFromNetwork;
import malkov.name.gygtest.utils.Utils;

public class Converter {

    public static List<Review> convertReviews(List<ReviewFromNetwork> rs) {
        if (rs == null) return Collections.emptyList();
        final List<Review> res = new ArrayList<>(rs.size());
        for (final ReviewFromNetwork r : rs) {

            res.add(convertReview(r));
        }
        return Collections.unmodifiableList(res);
    }

    @NonNull
    public static Review convertReview(ReviewFromNetwork r) {
        return new Review(
                r.getId(),
                Utils.convertFloatSafe(r.getRating()),
                r.getTitle(),
                r.getMessage(),
                r.getAuthor(),
                r.getDateStr(),
                r.isForeignLanguage(),
                r.getLanguageIcoCode(),
                r.getTravelerType(),
                r.getReviewerName(),
                r.getReviewerCountry()
        );
    }
}
