package malkov.name.gygtest;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import malkov.name.gygtest.db.model.Review;

public class Utils {

    @NonNull
    public static List<Review> mergeLists(List<Review> l1, List<Review> l2) {
        if (l1 == null && l2 == null) return Collections.emptyList();
        else if (l1 == null) return Collections.unmodifiableList(l2);
        else if (l2 == null) return Collections.unmodifiableList(l1);
        else {
            final List<Review> res = new ArrayList<>(l2.size() + l1.size());
            res.addAll(l1);
            res.addAll(l2);
            return res;
        }
    }

    public static float convertFloatSafe(@NonNull final String floatStr) {
        float res;
        try {
            res = Float.parseFloat(floatStr);
        } catch (Throwable t) {
            res = 0;
        }
        return res;
    }
}
