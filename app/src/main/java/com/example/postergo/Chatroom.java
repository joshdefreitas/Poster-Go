package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Chatroom extends AppCompatActivity {
    String roomName = "Batman";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

    }

    public void returnMain(View view){
        Intent r = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(r);
    }




}
