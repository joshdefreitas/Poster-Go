package com.example.postergo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

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

public class PosterContentLoader {

    private static final String TAG = "loadPosterContent";
    public static final String imgdbFileName = "posters_db.imgdb";
    private static final String
            imgdbUrl = "http://13.90.58.142:8081/get/downloadPoster/posters_db.imgdb";
    private static final String listUrl = "http://13.90.58.142:8081/get/downloadPoster/";
    private static final int TIME_OUT = 50000;
    private static final int RETRY_COUNT = 500;

    private Context context;
    private RequestQueue queue;
    private HashMap<String, Bitmap> imgMap;

    public PosterContentLoader(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.imgMap = new HashMap<>();
    }

    public void getContentList() {
        try {
            JsonObjectRequest listRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    listUrl,
                    new JSONObject("{poster_id:1}"),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: " + error.toString());
                        }
                    }
            );

        } catch (JSONException e) {
            Log.d(TAG, "getContentList: JSON Exception");
        };
    }

    public void getImgContent(String urlString, String imgName) {
        ImageRequest imageRequest = new ImageRequest(
                urlString,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgMap.putIfAbsent(imgName, response);
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
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

            }
        });

        queue.add(imageRequest);
    }

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
                        Log.d(TAG, "onErrorResponse: cannnot dl imgdb");
                    }
                },
                null
        );

        queue.add(request);
    }

    public Bitmap getUri(String filename) {
        return imgMap.get(filename);
    }

    /*
    private Uri saveImageToInternalStorage(Bitmap bitmap, String filename) {
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(context);

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images",MODE_PRIVATE);

        file = new File(file, filename + ".jpg");

        try{
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.parse(file.getAbsolutePath());
    }
    */
}
