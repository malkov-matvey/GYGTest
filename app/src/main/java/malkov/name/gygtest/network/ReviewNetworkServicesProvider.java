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

    public static GYGReviewsService buildService() {
        return new Retrofit.Builder()
                .baseUrl("https://www.getyourguide.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GYGReviewsService.class);
    }

}
