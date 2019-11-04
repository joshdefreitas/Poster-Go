package com.example.postergo;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

public class FileRequest extends Request<byte[]> {

    private final Response.Listener<byte[]> mListener;
    public Map<String, String> responseHeaders ;

    public FileRequest(int method, String mUrl , Response.Listener<byte[]> listener,
                       Response.ErrorListener errorListener) {

        super(method, mUrl, errorListener);
        setShouldCache(false);
        mListener = listener;
    }


    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        responseHeaders = response.headers;

        return Response.success( response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}
