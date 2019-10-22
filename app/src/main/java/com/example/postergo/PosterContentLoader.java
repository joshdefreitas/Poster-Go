package com.example.postergo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

    private static final int TIME_OUT = 50000;
    private static final int RETRY_COUNT = 500;

    private Context context;
    private RequestQueue queue;
    private JSONObject listResponse;
    private HashMap<Integer, String> description;
    private HashMap<Integer, Bitmap> imgMap;
    public View rightPanel;
    private TextView rPanelTextView;
    private ImageView rPanelImageView;

    public PosterContentLoader(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.description = new HashMap<>();
        this.imgMap = new HashMap<>();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        this.rightPanel = inflater.inflate(R.layout.right_panel, null);

        rPanelTextView = rightPanel.findViewById(R.id.right_text);
        rPanelImageView = rightPanel.findViewById(R.id.right_image);
    }

    public void getContentList(Integer id) {
        Map<String, Integer> params = new HashMap<>();
        params.put("poster_id", id);

            JsonObjectRequest listRequest = new JsonObjectRequest(
                    listUrl,
                    new JSONObject(params),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                listResponse = response;
                                rPanelTextView.setText(response.getString("description"));
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                            loadContent(id);
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

    private void loadContent(Integer id) {
        try{
            this.description.put(id, this.listResponse.getString("description"));
            this.getImgContent(imgUrlHead + this.listResponse.getString("filename"), id);
        } catch (JSONException e) {
            Log.d(TAG, "loadContent: JSONException");
            e.printStackTrace();
        }
    }

    public void getImgContent(String urlString, Integer id) {
        ImageRequest imageRequest = new ImageRequest(
                urlString,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {

                        imgMap.put(id, response);
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

    public Bitmap getImg(Integer id) {
        return imgMap.get(id);
    }

    public String getDescription(Integer id) {
        return description.get(id);
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
