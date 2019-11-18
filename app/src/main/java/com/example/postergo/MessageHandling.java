package com.example.postergo;

//import android.content.Intent; //To be used later to make intents
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageHandling extends FirebaseMessagingService {
    private static final String TAG = "MessageHandling";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        /* TODO: Implement this method to manage incoming notifications */

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();

            } else {
//                 Handle message within 10 seconds
                //handleNow();


            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String msg = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            startChatroom();

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onNewToken(String token){
        Log.d(TAG, "Refreshed token: " + token);

        //sendRegistrationToServer(token);

    }

//    TODO: Implement and use these methods
//    private void handleNow() {
//        /* TODO: Implement this method handleNow*/
//    }
//
//    private void scheduleJob() {
//        /* TODO: Implement this method scheduleJob*/
//    }
//
    public void startChatroom(){
        /* TODO: Implement this method startChatroom */
        Intent dialogIntent = new Intent(this, Chatroom.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

}
