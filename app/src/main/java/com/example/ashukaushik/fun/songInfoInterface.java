package com.example.ashukaushik.fun;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ashukaushik on 20/08/16.
 */
public interface SongInfoInterface {
    @GET("search")
    Call<SongInfoResponse> getSong(@Query("term") String term, @Query("country") String country, @Query("limit") int limit);
}
