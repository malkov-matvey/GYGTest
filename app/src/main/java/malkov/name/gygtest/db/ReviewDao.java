package malkov.name.gygtest.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Collection;
import java.util.List;

import io.reactivex.Single;
import malkov.name.gygtest.db.model.Review;

@Dao
public interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Review review);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Collection<Review> review);

    @Query("DELETE FROM reviews")
    void clearTable();

    @Query("SELECT * FROM reviews ORDER BY id DESC LIMIT 20 OFFSET :offset")
    Single<List<Review>> loadReviewsWithOffset(int offset);

}
