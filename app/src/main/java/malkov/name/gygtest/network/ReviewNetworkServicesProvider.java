package malkov.name.gygtest.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewNetworkServicesProvider {

    private static GYGReviewsService REVIEW_INSTANCE = null;
    private static final Object lock = new Object();

    public static GYGReviewsService buildService() {
        if (REVIEW_INSTANCE == null) {
            synchronized (lock) {
                if (REVIEW_INSTANCE == null) {
                    REVIEW_INSTANCE = new Retrofit.Builder()
                            .baseUrl("https://www.getyourguide.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build().create(GYGReviewsService.class);
                }
            }
        }
        return REVIEW_INSTANCE;
    }

}
