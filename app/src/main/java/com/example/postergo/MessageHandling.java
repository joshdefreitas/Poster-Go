package com.example.postergo;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageHandling extends FirebaseMessagingService {
    private static final String TAG = "MessageHandling";

    public MessageHandling() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Toast.makeText(getApplicationContext(),"Message received",Toast.LENGTH_LONG).show();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
                //startChatroom();
                //Toast.makeText(getApplicationContext(), "messaged received schedule", Toast.LENGTH_LONG).show();

            } else {
                // Handle message within 10 seconds
                handleNow();
                //Toast.makeText(getApplicationContext(), "message received handle", Toast.LENGTH_LONG).show();
                //startChatroom();

            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onNewToken(String token){
        Log.d(TAG, "Refreshed token: " + token);

        //sendRegistrationToServer(token);

    }

    private void handleNow() {

    }

    private void scheduleJob() {
    }

    public void startChatroom(){
        Intent c = new Intent(getApplicationContext(),Chatroom.class);
        //c.putExtra("New Message",message);
        startActivity(c);
    }

}
