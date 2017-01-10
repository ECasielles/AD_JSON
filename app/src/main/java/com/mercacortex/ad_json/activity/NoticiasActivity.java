package com.mercacortex.ad_json.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.mercacortex.ad_json.R;
import com.mercacortex.ad_json.adapter.MyRecyclerAdapter;
import com.mercacortex.ad_json.model.NewsFeed;
import com.mercacortex.ad_json.utils.NetworkController;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 20/12/16.
 */
public class NoticiasActivity extends AppCompatActivity implements View.OnClickListener {

    //public static final String WEB = "http://192.168.3.57/acceso/deportes.json";
    public static final String WEB = "https://portadaalta.club/acceso/deportes.json";
    RequestQueue queue;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    List<NewsFeed> feedsList = new ArrayList<NewsFeed>();
    MyRecyclerAdapter adapter;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this);

        //Initialize RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new MyRecyclerAdapter(this, feedsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //Getting Instance of Volley Request Queue
        queue = NetworkController.getInstance(this).getRequestQueue();
    }

    @Override
    public void onClick(View view) {
        if (view == fab)
            downloadGson();
    }

    private void downloadGson() {
        //Volley's inbuilt class to make Json array request
        JsonArrayRequest newsReq = new JsonArrayRequest(WEB, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                feedsList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        NewsFeed newsFeed = (NewsFeed)
                                gson.fromJson(String.valueOf(response.getJSONObject(i)), NewsFeed.class);
                        // adding movie to movies array
                        feedsList.add(newsFeed);
                    } catch (Exception e) {
                        //System.out.println(e.getMessage());
                        Toast.makeText(getApplicationContext(), "Exception: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } finally {
                        //Notify adapter about data changes
                        adapter.notifyItemChanged(i);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //System.out.println(error.getMessage());
                Toast.makeText(getApplicationContext(), "Volley exception: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        //Adding JsonArrayRequest to Request Queue
        queue.add(newsReq);
    }
}