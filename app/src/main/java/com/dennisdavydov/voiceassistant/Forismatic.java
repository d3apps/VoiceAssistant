package com.dennisdavydov.voiceassistant;

import android.support.v4.util.Consumer;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Forismatic {

    public static class AforismResult{
        @SerializedName("quoteText")
        public String quoteText;
    }


    public interface ForismaticService{
        @GET("api/1.0/?method=getQuote")
        Call<AforismResult> getForismatic (@Query("format") String format, @Query("lang") String lang);
    }

    public static void get(final Consumer <String> forismaticConsumer){
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://api.forismatic.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<AforismResult> callForism = retrofit
                .create(ForismaticService.class)
                .getForismatic("json","ru");

        callForism.enqueue(new Callback<AforismResult>() {
            @Override
            public void onResponse(Call<AforismResult> call, Response<AforismResult> response) {
                AforismResult aforismResult = response.body();
                String resultForism = aforismResult.quoteText;
                forismaticConsumer.accept(resultForism);
            }

            @Override
            public void onFailure(Call<AforismResult> call, Throwable t) {

            }
        });


    }

}
//https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=ru