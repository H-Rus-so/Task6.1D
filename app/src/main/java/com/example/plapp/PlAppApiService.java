package com.example.plapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Defines  backend endpoints.
 */
public interface PlAppApiService {
    @GET("getQuiz")
    Call<PlAppQuizResponse> getQuiz(@Query("topic") String topic);
}
