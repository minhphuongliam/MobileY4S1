package com.example.firebasetest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ItemDateAdapter extends RecyclerView.Adapter<ItemDateAdapter.ItemDateViewHolder> {

    private final List<String> dateList;
    private final Context context;

    public ItemDateAdapter(Context context, List<String> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public ItemDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_date_layout,parent,false);
        return new ItemDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDateViewHolder holder, int position) {
        String strdate = dateList.get(position);

        //parse strdate(String dd/MM/yy) to Date date
        SimpleDateFormat formatSTD = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = formatSTD.parse(strdate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //parse Date to formated String
        if(date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            String mD = "";
            String wD = "";

            mD = "" + calendar.get(Calendar.DAY_OF_MONTH);
            wD = " - " + calendar.get(Calendar.MONTH);
            switch (calendar.get(Calendar.DAY_OF_WEEK)){
                case 1:
                    wD = "Sun" + wD;
                    break;
                case 2:
                    wD = "Mon" + wD;
                    break;
                case 3:
                    wD = "Tue" + wD;
                    break;
                case 4:
                    wD = "Wed" + wD;
                    break;
                case 5:
                    wD = "Thu" + wD;
                    break;
                case 6:
                    wD = "Fri" + wD;
                    break;
                case 7:
                    wD = "Sat" + wD;
                    break;
            }

            holder.monthDate.setText(mD);
            holder.weekDate.setText(wD);
        }
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
