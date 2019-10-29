package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
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
    private int currentTime=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("PosterName"));

        ErrorMessage = findViewById(R.id.errorBox);
        textViewResult = findViewById(R.id.mainMessageView);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());
        editText = findViewById(R.id.editText3);

        getUpdates();


    }

    public void returnMain(View view){
        Intent r = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(r);
    }

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


//                        for (Message message : Updates) {
//                            String content = "";
//                            content += message.getUser_name() + ": " + message.getString();
//                            content += "\n\n";
//                            textViewResult.append(content);
//
//                        }

//                Message.setText(messageList.get(0).getString());
//                User.setText(messageList.get(0).getUser_name());




            }

            @Override
            public void onFailure(Call <List<Message>> call, Throwable t) {
                ErrorMessage.setText(t.getMessage());
            }
        });
    }

    public void createMessage(View view){
        getUpdates();
        final Message message = new Message(3,editText.getText().toString(),6,1,"Josh");

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://13.90.58.142:8081/")
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

                Message responseMessage = new Message(response.body().get_id(),response.body().getUser_id(),response.body().getString(),
                        response.body().getTime(),response.body().getRoom_number(),response.body().getUser_name());

//                    String content = "";
//                    content += responseMessage.getUser_name() + ": " + responseMessage.getString();
//                    content += "\n\n";
//                    textViewResult.append(content);
//                    ErrorMessage.setText(response.code());
                Updates.add(responseMessage);


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

    public void showUpdates(View view){
        getUpdates();
        textViewResult.setText("");
        for(Message message : Updates){
            String content = "";
            content += message.getUser_name() + ": " + message.getString();
            content += "\n\n";
            textViewResult.append(content);

        }
    }






}
