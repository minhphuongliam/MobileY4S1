package com.example.firebasetest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.R;

import java.util.List;

public class ItemTimeAdapter extends RecyclerView.Adapter<ItemTimeAdapter.ItemTimeViewHolder> {
    private final List<String> timeList;
    private final Context context;

    public ItemTimeAdapter(Context context, List<String> timeList) {
        this.timeList = timeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time_layout, parent, false);
        return new ItemTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTimeViewHolder holder, int position) {
        String strTime = timeList.get(position);
        holder.time.setText(strTime);
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public static class ItemTimeViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        public ItemTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timeSlot);
        }
    }
}
