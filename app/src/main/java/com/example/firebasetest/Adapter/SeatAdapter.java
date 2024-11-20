package com.example.firebasetest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.Model.Seat;
import com.example.firebasetest.R;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private final List<Seat> seatList;
    private final Context context;

    public SeatAdapter(Context context, List<Seat> seatList) {
        this.seatList = seatList;
        this.context = context;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat_layout, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);

        switch (seat.getStat()){
            case AVAILABLE:
                holder.seatImage.setImageResource(R.drawable.item_seat_available);
                break;
            case HOLDING:
                holder.seatImage.setImageResource(R.drawable.item_seat_holding);
                break;
            default:
            case UNAVAILABLE:
            case BOOKED:
                holder.seatImage.setImageResource(R.drawable.item_seat_unavailable);
        }
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }


    public static class SeatViewHolder extends RecyclerView.ViewHolder{
        ImageView seatImage;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            seatImage = itemView.findViewById(R.id.seatImg);
        }
    }
}
