package com.mercacortex.ad_json.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.mercacortex.ad_json.R;
import com.mercacortex.ad_json.model.NewsFeed;
import com.mercacortex.ad_json.utils.NetworkController;

import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private List<NewsFeed> feedsList;
    private Context context;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<NewsFeed> feedsList) {
        this.context = context;
        this.feedsList = feedsList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.singleitem, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsFeed feed = feedsList.get(position);
        //Pass the values of feeds object to Views
        holder.title.setText(feed.getTitle());
        holder.description.setText(feed.getDescription());
        holder.imageview.setImageUrl(feed.getImage(),
                NetworkController.getInstance(context).getImageLoader());
    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView title, description;
        private NetworkImageView imageview;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_view);
            description = (TextView) itemView.findViewById(R.id.description_view);
            // Volley's NetworkImageView which will load Image from URL
            imageview = (NetworkImageView) itemView.findViewById(R.id.thumbnail_view);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // Check if an item was deleted, but the user clicked it before the UI removed it
            if (position != RecyclerView.NO_POSITION) {
                // We can access the data within the views
                Toast.makeText(context, "Fecha: " + feedsList.get(position).getPubDate(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedsList.get(position).getLink()));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            } else {
                Toast.makeText(context, "Error: No hay un navegador", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
}
