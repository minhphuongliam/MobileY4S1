package com.example.firebasetest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.R;
import com.example.firebasetest.Model.Seat;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private final List<Seat> seatList;
    private final Context context;
    private OnItemSelectedListener listener;

    public SeatAdapter(Context context, List<Seat> seatList) {
        this.seatList = seatList;
        this.context = context;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    // Bước 1: Tạo Callback Interface
    public interface OnItemSelectedListener {
        void onItemSelected(String time, int position);
    }

    // Bước 2: Phương thức để cài đặt Listener từ Activity hoặc Fragment
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
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
            case TAPPING:
                holder.seatImage.setImageResource(R.drawable.item_seat_tapping);
                break;
            default:
            case UNAVAILABLE:
            case HOLDING:
            case BOOKED:
                holder.seatImage.setImageResource(R.drawable.item_seat_unavailable);
        }
        // Xử lý sự kiện click
        holder.itemView.setOnClickListener(v ->{
            // nếu ghế trống
            switch (seatList.get(holder.getAdapterPosition()).getStat()){
                case AVAILABLE:
                    // cập nhật status thành holding
                    seatList.get(holder.getAdapterPosition()).setStatus(Seat.Status.TAPPING);
                    // Làm mới tất cả các item
                    notifyDataSetChanged();
                    break;
                case TAPPING:
                    // cập nhật status thành available
                    seatList.get(holder.getAdapterPosition()).setStatus(Seat.Status.AVAILABLE);
                    // Làm mới tất cả các item
                    notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        });
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
