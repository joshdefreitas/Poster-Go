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

    }
}
