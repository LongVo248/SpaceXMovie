package cf.vandit.movie_app.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cf.vandit.movie_app.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static Retrofit getRetrofit() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.SPRING_BOOT_URL)
                .client(okHttpClient)
                .build();
    }

    public static AccountService getAccountService() {
        return getRetrofit().create(AccountService.class);
    }

    public static MovieService getMovieService() {
        return getRetrofit().create(MovieService.class);
    }
}
