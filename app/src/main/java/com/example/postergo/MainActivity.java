package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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

    public void startARCore(View view){
        Intent a = new Intent(getApplicationContext(),AR_Core.class);
        startActivity(a);
    }

    public void startRecommendations(View view){
        Intent r = new Intent(getApplicationContext(),Recommendations.class);
        startActivity(r);
    }
}
