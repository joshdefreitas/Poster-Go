package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chatroom extends AppCompatActivity {
    public static final String RECENT_MESSAGE = "com.example.Poster-Go.MESSAGE";
    private TextView ErrorMessage;
    private TextView textViewResult;
    private EditText editText;
    private String baseURL = "http://13.90.58.142:8081/";
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private List<Message> Updates;
    private TextView messageView;
    //private int currentTime=0; //To be used another time


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        TextView textView = findViewById(R.id.textView);

        if(getIntent().getStringExtra("PosterName") != null) {
            textView.setText(getIntent().getStringExtra("PosterName"));
        }

        ErrorMessage = findViewById(R.id.errorBox);
        textViewResult = findViewById(R.id.mainMessageView);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());
        editText = findViewById(R.id.enter_message);
        messageView = findViewById(R.id.messasge);

//        if(getIntent().getStringExtra("New Message") != null) {
//            newMessage = getIntent().getStringExtra("New Message");
//            textViewResult.setText("\n" +newMessage);
//        }
        getUpdates();

        if(getIntent().getStringExtra("action")!=null && getIntent().getStringExtra("action").equals("refresh")){
            getUpdates();
            showUpdates();
        }



    }

    /*
     * Allows user to return to the main activity
     * @param: View
     */

    public void returnMain(View view){
        Intent r = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(r);
    }

    /*
     * Gets updates to the current chatroom
     */

    public void getUpdates() {


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.90.58.142:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi =retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Message>> call = jsonPlaceHolderApi.getMessage();

                    call.enqueue(new Callback<List<Message>>() {
                        @Override
                        public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                            if(!response.isSuccessful()){
                                ErrorMessage.setText("Code: " +response.code());
                                return;
                            }
                            //errorMessage.setText("passed");

                            Updates = response.body();


            }

            @Override
            public void onFailure(Call <List<Message>> call, Throwable t) {
                ErrorMessage.setText(t.getMessage());
            }
        });
    }

    /*
     * Allows user to the message that was typed to the server and shows confirmation
     * @param: View
     */
    public void createMessage(View view){
        getUpdates();
        final Message message = new Message(3,editText.getText().toString(),6,1,GlobalVariablesHelper.user_name);
        messageView.setText(editText.getText().toString());

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi =retrofit.create(JsonPlaceHolderApi.class);

        Call<Message> call = jsonPlaceHolderApi.createMessage(message);

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(!response.isSuccessful()){
                    ErrorMessage.setText("Code: "+ response.code());
                    return;
                }

                Message responseMessage = new Message(response.body().getId(),response.body().getUserId(),response.body().getString(),
                        response.body().getTime(),response.body().getRoomNumber(),response.body().getUserName());

//                    String content = "";
//                    content += responseMessage.getUser_name() + ": " + responseMessage.getString();
//                    content += "\n\n";
//                    textViewResult.append(content);
//                    ErrorMessage.setText(response.code());
                Updates.add(responseMessage);
                showUpdates();


                Toast toast = Toast.makeText(getApplicationContext(),
                        "Message was sent",
                        Toast.LENGTH_SHORT);

                toast.show();

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                textViewResult.setText((t.getMessage()));
            }
        });



    }

    /*
     * Allows user to manually show updates to the current chatroom
     * @param: View
     */
    public void showUpdates(View view){
        getUpdates();
        textViewResult.setText("");
        for(Message message : Updates){
            String content = "";
            content += message.getUserName() + ": " + message.getString();
            content += "\n\n";
            textViewResult.append(content);

        }
        Toast.makeText(this, "Chat updated", Toast.LENGTH_SHORT).show();
    }

    public void showUpdates(){
        getUpdates();
        textViewResult.setText("");

        if(Updates != null) {
            for (Message message : Updates) {
                String content = "";
                content += message.getUserName() + ": " + message.getString();
                content += "\n\n";
                textViewResult.append(content);
                Toast.makeText(this, "Chat updated", Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(this,"No new updates",Toast.LENGTH_SHORT).show();
        }
    }








}
