package com.mercacortex.ad_json.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mercacortex.ad_json.analisis.AnalisisJSON;
import com.mercacortex.ad_json.utils.MySingleton;
import com.mercacortex.ad_json.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PrimitivaRedActivity extends AppCompatActivity {

    public static final String TAG = "MyTag";
    public static final String WEB = "http://portadaalta.club/acceso/primitiva.json";
    Button mButton;
    TextView mTextView;
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primitiva_red);
        mButton = (Button) findViewById(R.id.btnMostrarPrimitivaRed);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descarga();
            }
        });
        mTextView = (TextView) findViewById(R.id.txvPrimitivaRed);
        mRequestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    private void descarga() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, WEB, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mTextView.setText(AnalisisJSON.analizarPrimitiva(response));
                        } catch (JSONException e) {
                            Toast.makeText(PrimitivaRedActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PrimitivaRedActivity.this, error.getMessage(), Toast.LENGTH_LONG);
                    }
                });
        // Set the tag on the request.
        jsObjRequest.setTag(TAG);
        // Set retry policy
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 1, 1));
        // Add the request to the RequestQueue.
        mRequestQueue.add(jsObjRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }
}
