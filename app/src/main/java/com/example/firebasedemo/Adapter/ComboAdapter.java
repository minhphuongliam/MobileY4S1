package com.example.firebasedemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasedemo.Model.Combo;
import com.example.firebasedemo.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ComboViewHolder> {

    // Interface to notify when combo quantities change
    public interface OnComboQuantityChangeListener {
        void onQuantityChanged(double totalAmount);
    }

    private Context context;
    private List<Combo> combos;
    private Map<Integer, Integer> quantities = new HashMap<>();
    private OnComboQuantityChangeListener quantityChangeListener;
    private float ticketPrice; // Add ticket price field

    // Updated constructor to accept ticket price
    public ComboAdapter(Context context, List<Combo> combos,
                        float ticketPrice,
                        OnComboQuantityChangeListener listener) {
        this.context = context;
        this.combos = combos;
        this.ticketPrice = ticketPrice;
        this.quantityChangeListener = listener;

        // Initialize quantities for each combo
        for (int i = 0; i < combos.size(); i++) {
            quantities.put(i, 0);
        }

        if (quantityChangeListener != null) {
            quantityChangeListener.onQuantityChanged(ticketPrice);
        }
    }

    @NonNull
    @Override
    public ComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_combo, parent, false);
        return new ComboViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboViewHolder holder, int position) {
        Combo combo = combos.get(position);
        int quantity = quantities.get(position);

        // Set combo item details
        holder.tvName.setText(combo.getName());
        holder.tvDescription.setText(combo.getDescription());
        holder.tvPrice.setText(String.format("Price: %.0f $", combo.getPrice()));
        holder.tvQuantity.setText(String.valueOf(quantity));
        Glide.with(context).load(combo.getImageUrl()).into(holder.ivImage);

        // Increase button click listener
        holder.btnIncrease.setOnClickListener(v -> {
            quantities.put(position, quantity + 1);
            notifyItemChanged(position);
            notifyTotalAmountChanged();
        });

        // Decrease button click listener
        holder.btnDecrease.setOnClickListener(v -> {
            if (quantity > 0) {
                quantities.put(position, quantity - 1);
                notifyItemChanged(position);
                notifyTotalAmountChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return combos.size();
    }

    // Calculate total amount including ticket price and combo prices
    private void notifyTotalAmountChanged() {
        double totalAmount = ticketPrice; // Start with ticket price

        // Add combo prices based on their quantities
        for (int i = 0; i < combos.size(); i++) {
            double comboPrice = combos.get(i).getPrice();
            int quantity = quantities.get(i);
            totalAmount += comboPrice * quantity;
        }

        // Notify listener with total amount
        if (quantityChangeListener != null) {
            quantityChangeListener.onQuantityChanged(totalAmount);
        }
    }

    public static class ComboViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription, tvPrice, tvQuantity;
        ImageView ivImage;
        Button btnIncrease, btnDecrease;

        public ComboViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_combo_name);
            tvDescription = itemView.findViewById(R.id.tv_combo_description);
            tvPrice = itemView.findViewById(R.id.tv_combo_price);
            ivImage = itemView.findViewById(R.id.iv_combo_image);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
        }
    }
}