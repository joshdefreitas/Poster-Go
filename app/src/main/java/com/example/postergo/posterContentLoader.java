package com.example.postergo;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class posterContentLoader {

    private static final String TAG = "loadPosterContent";
    private static final int TIME_OUT = 50000;
    private static final int RETRY_COUNT = 500;

    private Context context;
    private RequestQueue queue;
    private HashMap<String, Uri> fileMap;

    public posterContentLoader(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
        this.fileMap = new HashMap<>();
    }

    public void getImgContent(String urlString, String filename) {
        try {
            ImageRequest imageRequest = new ImageRequest(
                    urlString,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {

                            Uri uri = saveImageToInternalStorage(response, filename);
                            fileMap.putIfAbsent(filename, uri);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getUri(String filename) {
        return fileMap.get(filename);
    }

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
}
