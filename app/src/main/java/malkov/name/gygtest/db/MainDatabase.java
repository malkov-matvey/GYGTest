package malkov.name.gygtest.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import malkov.name.gygtest.db.model.Review;

@Database(entities = {Review.class}, version = 1, exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {
    public abstract ReviewDao reviews();

    private static String dbName = "main_db";

    private static MainDatabase INSTANCE;

    private static final Object lock = new Object();

    public static MainDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            MainDatabase.class,
                            dbName
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
