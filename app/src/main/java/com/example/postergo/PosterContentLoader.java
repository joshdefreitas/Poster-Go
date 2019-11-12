package com.example.postergo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PosterContentLoader {

    private static final String TAG = "loadPosterContent";
    public static final String imgdbFileName = "posters_db.imgdb";

    private static final String
            imgdbUrl = "http://13.90.58.142:8081/get/downloadPoster/posters_db.imgdb";
    private static final String
            listUrl = "http://13.90.58.142:8081/post/download/";
    private static final String
            imgUrlHead = "http://13.90.58.142:8081/get/downloadPoster/";
    private static final String
            historyUrl = "http://13.90.58.142:8081/post/userViewHistory";
    private static final String
            likeUrl = "http://13.90.58.142:8081/put/userLike";

    private static final int TIME_OUT = 50000;
    private static final int RETRY_COUNT = 500;

    private Context context;
    private RequestQueue queue;
    private JSONObject listResponse;
    private TextView rPanelTextView;
    private ImageView rPanelImageView;
    public WebView lWebView;

    public View rightPanel;
    public View leftPanel;

    /*
    * Create new posterContentLoader,
    * Get context, initialize volley queue, inflate the right panel view
    * Setup the onClickListener for "like" button
    *
    * Param:
    * context: the current context
    */
    public PosterContentLoader(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.rightPanel = inflater.inflate(R.layout.right_panel, null);
        this.leftPanel = inflater.inflate(R.layout.left_panel, null);

        rPanelTextView = rightPanel.findViewById(R.id.right_text);
        rPanelImageView = rightPanel.findViewById(R.id.right_image);
        lWebView = leftPanel.findViewById(R.id.left_webview);
        Button likeButton = rightPanel.findViewById(R.id.like_button);

        lWebView.getSettings().setDisplayZoomControls(true);
        lWebView.loadUrl("https://m.imdb.com");

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postHistoryAndLike(1, GlobalVariablesHelper.user_name);
                Log.d(TAG, "onClick: liked");
            }
        });
    }

    /*
    * Load poster description and supplementary image to right panel view
    *
    * Param:
    * id: the poster id from augmentedImageDatabase
    */
    public void getContent(Integer id) {
        Map<String, Integer> params = new HashMap<>();
        params.put("poster_id", id);

            JsonObjectRequest listRequest = new JsonObjectRequest(
                    listUrl,
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            listResponse = response;
                            postHistoryAndLike(0, GlobalVariablesHelper.user_name);
                            loadContentToView();
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: content list: " + error.toString());
                        }
                    }
            );

            queue.add(listRequest);
    }




    /*
    * Load augmentedImageDatabase from the backend,
    * and save it on local storage
    */
    public void getImgdb() {
        FileRequest request = new FileRequest(
                Request.Method.GET,
                imgdbUrl,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        try {
                            FileOutputStream outputStream =
                                    context.openFileOutput(imgdbFileName, Context.MODE_PRIVATE);

                            outputStream.write(response);
                            outputStream.close();

                        } catch (Exception e) {
                            Log.d(TAG, "onResponse: cannot save imgdb");
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: dl imgdb: " + error.getMessage());
                    }
                }
        );

        queue.add(request);
    }

    /*
    * Post view history to the backend,
    * put the "liked" information to the backend if user liked the poster
    *
    * Param:
    * like: 0 for post history, 1 for put like
    * username: current user who viewed and liked the poster
    */
    public void postHistoryAndLike(int like, String username) {
        try {
            listResponse.put("like", like);
            listResponse.put("user_name", username);
        } catch (JSONException e) {
            Log.d(TAG, "postHistoryAndLike: ");
            e.printStackTrace();
        }

        Log.d(TAG, "postHistoryAndLike: " + listResponse.toString());

        String url = "";
        int requestMethod = 0;

        if (like == 0) {
            url = historyUrl;
            requestMethod = Request.Method.POST;
        } else {
            url = likeUrl;
            requestMethod = Request.Method.PUT;
        }

        JsonObjectRequest historyRequest = new JsonObjectRequest(
                requestMethod,
                url,
                listResponse,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // No response for this request
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                    }
                }
        );

        queue.add(historyRequest);

    }

    private void loadContentToView() {
        try{
            rPanelTextView.setText(listResponse.getString("description"));
            this.getImgContent(imgUrlHead + this.listResponse.getString("filename"));
        } catch (JSONException e) {
            Log.d(TAG, "loadContentToView: JSONException");
            e.printStackTrace();
        }
    }

    private void getImgContent(String urlString) {
        ImageRequest imageRequest = new ImageRequest(
                urlString,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        rPanelImageView.setImageBitmap(response);
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: load img: " + error.getMessage());
                    }
                }
        );

        imageRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return TIME_OUT;
            }

            @Override
            public int getCurrentRetryCount() {
                return RETRY_COUNT;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Log.d(TAG, "retry: " + error.toString());
            }
        });

        queue.add(imageRequest);
    }
}
