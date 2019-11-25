package com.example.postergo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;



import com.google.android.material.textfield.TextInputEditText;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
//import com.google.firebase.messaging.FirebaseMessaging;





public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveFCMToken();

        if (getIntent().getStringExtra("action") != null && getIntent().getStringExtra("action").equals("refresh")) {
            Intent chatFromMainIntent = new Intent(getApplicationContext(), Chatroom.class);
            startActivity(chatFromMainIntent);
        }

    }

    /*
    * Allows user to start the chatroom activity from the home screen UI
    * @param: View
    */
    public void startChatroom(View view){
        Intent c = new Intent(getApplicationContext(),Chatroom.class);
        c.putExtra("PosterName","Chatroom");
        c.putExtra("PosterID",123456789);
        startActivity(c);
    }

    /*
     * Allows user to start the Recommendations activity from the home screen UI
     * @param: View
     */

    public void startRecommNew(View view){
        Intent rn = new Intent(getApplicationContext(),RecommendationsNew.class);
        startActivity(rn);
    }

    /*
     * Allows user to start the ARCOre activity from the home screen UI
     * @param: View
     */
    public void startARCore(View view){
        Intent r = new Intent(getApplicationContext(),ARCore.class);
        startActivity(r);
    }


    /*
     * Generates token for current instance
     */
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
                        //String token = task.getResult().getToken();

                    }
                });
        // [END retrieve_current_token]

    }

    /*
     * Sends generated token to be saved on the server
     * @param: token to be saved
     */
//    public void sendToServer(String token){
//        //successMsg.setText("Token: " +token);
//        //TODO: Implement api call to server
//
//
//    }



    public void getUserName(View view) {
        TextInputEditText usernameInput = findViewById(R.id.username_input);
        TextView usernamePrompt = findViewById(R.id.username_prompt);

        GlobalVariablesHelper.user_name = usernameInput.getText().toString();
        usernamePrompt.setText("Username: " + GlobalVariablesHelper.user_name);

    }




}
