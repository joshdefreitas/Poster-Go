package com.example.postergo;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

    public interface JsonPlaceHolderApi {

        @GET("post/test")
        Call<List<Message>> getMessage();
}
