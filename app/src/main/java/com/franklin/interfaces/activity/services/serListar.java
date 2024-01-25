package com.franklin.interfaces.activity.services;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class serListar {


    public interface ServiceCallback {
        void onSuccess(JSONArray response);
        void onError(String errorMessage);
    }

    private Context context;

    public serListar(Context context) {
        this.context = context;
    }
    public void listar(String url, final ServiceCallback callback) {
        JsonArrayRequest request = new JsonArrayRequest(new URL().endPoint+url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    // Llamamos al método onSuccess del callback
                    callback.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de error, llamamos al método onError del callback
                callback.onError(error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}

