package com.example.firebasedemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.firebasedemo.R;

import java.util.List;

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.AdViewHolder> {

    private final List<String> adList; // Danh sách URL của các poster
    private final Context context;

    public AdAdapter(Context context, List<String> adList) {
        this.context = context;
        this.adList = adList;
    }

    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ad, parent, false);
        return new AdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {
        String imageUrl = adList.get(position);
        // Sử dụng Glide để load hình ảnh
        Glide.with(context)
                .load(imageUrl)
                .into(holder.imgAd);
    }

    @Override
    public int getItemCount() {
        return adList.size();
    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAd;

        public AdViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAd = itemView.findViewById(R.id.imgAd);
        }
    }
}
