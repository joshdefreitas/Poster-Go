package com.example.postergo;

import androidx.annotation.NonNull;
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
import android.widget.Toast;


import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.quickstart.fcm.R;




public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView successMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        successMsg =findViewById(R.id.textView2);


        saveFCMToken();

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

    public void saveFCMToken(){
        // Get token
        // [START retrieve_current_token]

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        String msg = token;
                        successMsg.setText(msg);

                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]

    }

    public void sendToServer(String token){
        successMsg.setText("Token: " +token);


    }



}
