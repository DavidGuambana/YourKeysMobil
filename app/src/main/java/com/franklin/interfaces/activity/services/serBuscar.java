package com.franklin.interfaces.activity.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class serBuscar {

    public interface ServiceCallback {
        void onSuccess(Object response);
        void onError(String errorMessage);
    }

        private Context context;

        public serBuscar(Context context) {
            this.context = context;
        }

        public void buscar(String url, final ServiceCallback callback) {
            JsonObjectRequest request = new JsonObjectRequest(new URL().endPoint+url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            callback.onSuccess(response);
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