package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AR_Core extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar__core);
    }

    public void returnMain(View view){
        Intent r = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(r);
    }
}
