package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Chatroom extends AppCompatActivity {
    public static final String RECENT_MESSAGE = "com.example.Poster-Go.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("PosterName"));


    }

    public void returnMain(View view){
        Intent r = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(r);
    }

    public void saveMessage(View view){
        EditText editText = (EditText) findViewById(R.id.editText3);
        String message = editText.getText().toString();
        TextView textView = findViewById(R.id.textView4);
        textView.setText((message));
    }




}
