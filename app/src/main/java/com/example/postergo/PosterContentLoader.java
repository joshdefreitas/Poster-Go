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
    private static final String apiUrl = "http://13.90.58.142:8081/get/downloadPoster/";
    private static final int TIME_OUT = 50000;
    private static final int RETRY_COUNT = 500;

    private Context context;
    private RequestQueue queue;
    private HashMap<String, String> description;
    private HashMap<String, Bitmap> imgMap;

    public PosterContentLoader(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.imgMap = new HashMap<>();
    }

    public void getContentList(String id) {
        try {
            JsonObjectRequest listRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    apiUrl,
                    new JSONObject("{poster_id:" + id + "}"),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "onResponse: content list: " + response.toString());
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

        } catch (JSONException e) {
            Log.d(TAG, "getContentList: JSON Exception");
        }
    }

    public void getImgContent(String urlString, String id) {
        ImageRequest imageRequest = new ImageRequest(
                urlString,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgMap.put(id, response);
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
                        Log.d(TAG, "onErrorResponse: dl imgdb: " + error.getMessage());
                    }
                },
                null
        );

        queue.add(request);
    }

    public Bitmap getImg(String id) {
        return imgMap.get(id);
    }

    public String getDescription(String id) {return description.get(id);}

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
