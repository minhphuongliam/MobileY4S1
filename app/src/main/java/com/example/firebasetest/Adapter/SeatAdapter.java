package com.example.firebasetest.Adapter;

import static com.example.firebasetest.Model.Seat.Status;

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

    public SeatAdapter(List<Seat> seatList, Context context) {
        this.seatList = seatList;
        this.context = context;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_seat_layout, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);

        switch (seat.getStat()){
            case AVAILABLE:
                holder.seat.setImageResource(R.drawable.item_seat_available);
                break;
            case HOLDING:
                holder.seat.setImageResource(R.drawable.item_seat_holding);
                break;
            case UNAVAILABLE:
            case BOOKED:
                holder.seat.setImageResource(R.drawable.item_seat_unavailable);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }


    public static class SeatViewHolder extends RecyclerView.ViewHolder{
        ImageView seat;
        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            seat = itemView.findViewById(R.id.seatImg);
        }
    }
}
