package com.example.firebasetest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ItemDateAdapter extends RecyclerView.Adapter<ItemDateAdapter.ItemDateViewHolder> {

    private final List<String> dateList;
    private final Context context;
    private int selectedPosition = RecyclerView.NO_POSITION; // -1 nghĩa là chưa có item nào được chọn.
    private OnItemSelectedListener listener;

    public ItemDateAdapter(Context context, List<String> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    // Bước 1: Tạo Callback Interface
    public interface OnItemSelectedListener {
        void onItemSelected(String date, int position);
    }

    // Bước 2: Phương thức để cài đặt Listener từ Activity hoặc Fragment
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_date_layout,parent,false);
        return new ItemDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDateViewHolder holder, int position) {
        String strDate = dateList.get(position);

        // Parse strDate (String dd/MM/yyyy) to Date object
        SimpleDateFormat formatSTD = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = formatSTD.parse(strDate);
        } catch (ParseException e) {
            // Nếu không parse được, bỏ qua và không hiển thị gì
            holder.monthDate.setText("");
            holder.weekDate.setText("");
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.date_unselected));
            return;
        }

        // Nếu parse thành công, xử lý dữ liệu
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Lấy ngày và tháng
            String mD = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String wD;

            // Lấy thứ trong tuần
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    wD = "Sun";
                    break;
                case Calendar.MONDAY:
                    wD = "Mon";
                    break;
                case Calendar.TUESDAY:
                    wD = "Tue";
                    break;
                case Calendar.WEDNESDAY:
                    wD = "Wed";
                    break;
                case Calendar.THURSDAY:
                    wD = "Thu";
                    break;
                case Calendar.FRIDAY:
                    wD = "Fri";
                    break;
                case Calendar.SATURDAY:
                    wD = "Sat";
                    break;
                default:
                    wD = ""; // Trường hợp không hợp lệ
            }

            // Hiển thị ngày và thứ
            holder.monthDate.setText(mD);
            holder.weekDate.setText(wD + " - " + (calendar.get(Calendar.MONTH) + 1)); // +1 vì tháng bắt đầu từ 0
        }

        // Đặt màu nền dựa trên trạng thái được chọn
        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.date_selected));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.date_unselected));
        }

        // Xử lý sự kiện click
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = holder.getAdapterPosition(); // Cập nhật vị trí được chọn
            notifyDataSetChanged(); // Làm mới tất cả các item

            // Gọi callback nếu listener không null
            if (listener != null) {
                listener.onItemSelected(strDate, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public static class ItemDateViewHolder extends RecyclerView.ViewHolder{
        TextView    monthDate,
                    weekDate;

        public ItemDateViewHolder(View itemView){
            super(itemView);
            monthDate = itemView.findViewById(R.id.monthDate);
            weekDate  = itemView.findViewById(R.id.weekDate);
        }
    }
}
