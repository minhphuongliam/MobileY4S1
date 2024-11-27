package com.example.retrofit2_tmdb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit2_tmdb.model.Combo;

import java.util.Arrays;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerCombos;
    private ComboAdapter comboAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Setup RecyclerView
        recyclerCombos = findViewById(R.id.recycler_combos);
        recyclerCombos.setLayoutManager(new LinearLayoutManager(this));

        // Mock Combo Data
        List<Combo> combos = Arrays.asList(
                new Combo("Beta Combo 69oz", "1 Bắp + 1 Nước có gaz", 2.8, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Family Combo 69oz", "2 Bắp + 2 Nước + 2 Snack", 9.5, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Sweet Combo 69oz", "1 Bắp + 2 Nước", 4.6, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Tuan dep trai", "1 Khoai + 2 Trung", 99999, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png")
        );

        comboAdapter = new ComboAdapter(this, combos);
        recyclerCombos.setAdapter(comboAdapter);
    }
}

