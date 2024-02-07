package com.franklin.interfaces.activity.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class serEliminar {

    public interface ServiceCallback {
        void onSuccess(Object response);
        void onError(String errorMessage);
    }
    private Context context;

    public serEliminar(Context context) {
        this.context = context;
    }


    public void eliminar(String url, final ServiceCallback callback) {
        StringRequest request = new StringRequest(Request.Method.DELETE, new RUTA().endPoint + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("")) {
                            callback.onSuccess("Eliminación exitosa");
                        } else {
                            callback.onError("Respuesta inesperada: " + response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}
