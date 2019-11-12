package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recommendations extends AppCompatActivity {

    private TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        textView = findViewById(R.id.recommendations);

    }

    /*
     * Allows user to return to the main activity
     */
    public void returnMain(View view){
        Intent r = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(r);
    }

    /*
     * Allows user to display recommendations from the server
     * @param: View
     */

    public void showRecommendations(View view){
        //TODO: Implement method to show recommendations received from server
    }

    /* Test Method
     * Makes a recommendation request and displays results
     * @param: View
     */
    public void getRecomm(View view) {
        textView.setText("");

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.90.58.142:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Poster>> call = jsonPlaceHolderApi.getRecommendations();

        call.enqueue(new Callback<List<Poster>>() {
            @Override
            public void onResponse(Call<List<Poster>> call, Response<List<Poster>> response) {

                if(!response.isSuccessful()){
                    textView.setText("Code: " +response.code());
                    return;
                }
                //errorMessage.setText("passed");
                List<Poster> posters;
                posters = response.body();


                        for (Poster poster : posters) {
                            String content = "";
                            content += poster.getMovietype() + ": " + poster.getDescription();
                            content += "\n\n";
                            textView.append(content);

                        }

//                Message.setText(messageList.get(0).getString());
//                User.setText(messageList.get(0).getUser_name());




            }

            @Override
            public void onFailure(Call <List<Poster>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}