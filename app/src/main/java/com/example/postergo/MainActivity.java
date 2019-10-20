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
        c.putExtra("PosterName","Batman");
        c.putExtra("PosterID",123456789);
        startActivity(c);
    }
}
