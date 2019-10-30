package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void startChatroom(View view){
        Intent c = new Intent(getApplicationContext(),Chatroom.class);
        c.putExtra("PosterName","POSTER NAME GOES HERE");
        c.putExtra("PosterID",123456789);
        startActivity(c);
    }


    public void startRecommendations(View view){
        Intent r = new Intent(getApplicationContext(),Recommendations.class);
        startActivity(r);
    }

    public void startARCore(View view){
        Intent r = new Intent(getApplicationContext(),ARCore.class);
        startActivity(r);
    }

    public void getUserName(View view) {
        TextInputEditText usernameInput = findViewById(R.id.username_input);
        TextView usernamePrompt = findViewById(R.id.username_prompt);

        GlobalVariables.user_name = usernameInput.getText().toString();
        usernamePrompt.setText("Username: " + GlobalVariables.user_name);

    }


}
