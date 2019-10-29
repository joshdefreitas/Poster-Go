package com.example.postergo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

        @GET("/get/findAllChatByRoomNumber/1&0")
        Call<List<Message>> getMessage();

        @POST("post/updateChat")
        Call<Message> createMessage(@Body Message message);

        @GET("get/rec/field=movietype&value=action")
        Call <List<Poster>> getRecommendations();
}
