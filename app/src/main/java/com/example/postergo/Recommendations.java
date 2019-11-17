package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Recommendations extends AppCompatActivity {

    private String baseURL = "http://13.90.58.142:8081/";
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView textView ;
    private String user_name = "Brant";
    private List<RecommJSON> recommJSONList;

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
        textView.setText("");
    }

    public void showRecommendations(List<RecommJSON> recommendations){
        //TODO: Implement method to show recommendations received from server
        textView.setText("");
    }

    /* Test Method
     * Makes a recommendation request and displays results
     * @param: View
     */
    public void getRecomm(View view) {

        final User user = new User(user_name);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi =retrofit.create(JsonPlaceHolderApi.class);
        Call<List<RecommJSON>> call = jsonPlaceHolderApi.getRecommendations(user);

        call.enqueue(new Callback<List<RecommJSON>>() {
            @Override
            public void onResponse(Call<List<RecommJSON>> call, Response<List<RecommJSON>> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code: "+ response.code());
                    return;
                }

                recommJSONList = response.body();

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Recommendations received",
                        Toast.LENGTH_SHORT);

                toast.show();

                showRecommendations(recommJSONList);


            }

            @Override
            public void onFailure(Call<List<RecommJSON>> call, Throwable t) {

            }
        });



    }
}
