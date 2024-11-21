package com.example.firebasetest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.R;

import java.util.List;

public class ItemTimeAdapter extends RecyclerView.Adapter<ItemTimeAdapter.ItemTimeViewHolder> {
    private final List<String> timeList;
    private final Context context;
    private int selectedPosition = RecyclerView.NO_POSITION; // -1 nghĩa là chưa có item nào được chọn.
    private OnItemSelectedListener listener;

    public ItemTimeAdapter(Context context, List<String> timeList) {
        this.timeList = timeList;
        this.context = context;
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
    public ItemTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time_layout, parent, false);
        return new ItemTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTimeViewHolder holder, int position) {
        String strTime = timeList.get(position);
        holder.time.setText(strTime);

        // Đặt màu nền dựa trên trạng thái được chọn
        if (position == selectedPosition) {
            holder.time.setBackgroundResource(R.drawable.item_time_selected_background);
            holder.time.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        } else {
            holder.time.setBackgroundResource(R.drawable.item_time_default_background);
            holder.time.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black2));
        }

        // Xử lý sự kiện click
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition(); // Cập nhật vị trí được chọn
            notifyDataSetChanged(); // Làm mới tất cả các item

            // Gọi callback nếu listener không null
            if (listener != null) {
                listener.onItemSelected(strTime, position);
            }
        });
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
