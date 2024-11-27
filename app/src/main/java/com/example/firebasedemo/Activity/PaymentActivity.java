package com.example.firebasedemo.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.firebasedemo.Adapter.ComboAdapter;
import com.example.firebasedemo.Model.Combo;
import com.example.firebasedemo.R;

import java.util.Arrays;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private TextView tvTotalAmount, tvAmountToPay;
    private RecyclerView recyclerView;
    private List<Combo> comboList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        tvTotalAmount = findViewById(R.id.tv_total);
        tvAmountToPay = findViewById(R.id.tv_amount_due);
        recyclerView = findViewById(R.id.recycler_combos);

        // Initialize combo list and adapter
        comboList = getCombos(); // Fetch or initialize your combo list here
        ComboAdapter adapter = new ComboAdapter(this, comboList, this::updateAmountViews);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void updateAmountViews(double totalAmount) {
        double discount = calculateDiscount(totalAmount); // Implement your discount logic
        double amountToPay = totalAmount - discount;

        tvTotalAmount.setText(String.format("Total Amount: %.2f $", totalAmount));
        tvAmountToPay.setText(String.format("Amount to Pay: %.2f $", amountToPay));
    }

    private double calculateDiscount(double totalAmount) {
        // Example discount logic (e.g., 10% discount if total > 100)
        return totalAmount > 100 ? totalAmount * 0.10 : 0;
    }

    private List<Combo> getCombos() {
        // Dummy combo list for illustration
        return Arrays.asList(
                new Combo("Beta Combo 69oz", "1 Bắp + 1 Nước có gaz", 2.8, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Family Combo 69oz", "2 Bắp + 2 Nước + 2 Snack", 9.5, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Sweet Combo 69oz", "1 Bắp + 2 Nước", 4.6, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Tuan dep trai", "1 Khoai + 2 Trung", 99, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png")
        );
    }
}

