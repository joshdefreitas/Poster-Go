package com.example.postergo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecommendationsNew extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String baseURL = "http://13.90.58.142:8081/";
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView textView ;
    private String user_name = "Brant3";
    private ArrayList<Poster> postersRecomm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations_new);

        textView = findViewById(R.id.textView2);
        recyclerView = (RecyclerView) findViewById(R.id.myRecycle);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this, getPosters());
        recyclerView.setAdapter(mAdapter);

    }

    private ArrayList<Poster> getPosters(){
        ArrayList<Poster> posters = new ArrayList<>();


        Poster p = new Poster();
        p.setDescription("Poster description");
        p.setFilename("0.jpg");

        posters.add(p);

        Poster q = new Poster();
        q.setDescription("Poster description 2");
        q.setFilename("1.jpg");

        posters.add(q);

        Poster r = new Poster();
        r.setDescription("Poster description 3");
        r.setFilename("2.jpg");

        posters.add(r);

        Poster s = new Poster();
        s.setDescription("Poster description 4");
        s.setFilename("3.jpg");

        posters.add(s);



    return posters;
    }

    /*
     * Makes a recommendation request and displays results
     * @param: View
     */
    public void getRecomm(View view) {

        final User user = new User(user_name);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi =retrofit.create(JsonPlaceHolderApi.class);
        Call<ArrayList<Poster>> call = jsonPlaceHolderApi.getRecommendations(user);

        call.enqueue(new Callback<ArrayList<Poster>>() {
            @Override
            public void onResponse(Call<ArrayList<Poster>> call, Response<ArrayList<Poster>> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code: "+ response.code());
                    return;
                }

                postersRecomm = response.body();

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Recommendations received",
                        Toast.LENGTH_SHORT);

                toast.show();


                showRecommendations(postersRecomm);


            }

            @Override
            public void onFailure(Call<ArrayList<Poster>> call, Throwable t) {
                textView.setText("no response");
                //textView.setText(t.getMessage());
            }
        });



    }

    public void showRecommendations(ArrayList<Poster> posters){
        mAdapter = new MyAdapter(this, posters);
        recyclerView.setAdapter(mAdapter);
    }
}
