package com.example.firebasedemo.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.Adapter.ComboAdapter;
import com.example.firebasedemo.Model.Booking;
import com.example.firebasedemo.Model.Combo;
import com.example.firebasedemo.Model.Screening;
import com.example.firebasedemo.Model.Seat;
import com.example.firebasedemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    // UI Components
    private TextView tvTotalAmount, tvAmountToPay, movieTitle, movieDetails, screeningDate, screeningTime, screeningRoom, seatsView;
    private RecyclerView recyclerView;

    // Data
    private List<Combo> comboList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize UI Components
        initUI();

        // Get data from Intent
        Booking booking = getIntent().getParcelableExtra("booking_data");
        Log.d("Tag", "Received booking: " + booking);

        if (booking != null) {
            populateBookingDetails(booking);
        }

        // Initialize combo list and set up adapter
        comboList = getCombos();
        setupRecyclerView(comboList, booking.getPrice());
    }

    // Initialize all UI components.

    private void initUI() {
        tvTotalAmount = findViewById(R.id.tv_total);
        tvAmountToPay = findViewById(R.id.tv_amount_due);
        recyclerView = findViewById(R.id.recycler_combos);
        movieTitle = findViewById(R.id.tv_movie_title);
        movieDetails = findViewById(R.id.tv_movie_details);
        screeningDate = findViewById(R.id.tv_screening_date);
        screeningTime = findViewById(R.id.tv_screening_time);
        screeningRoom = findViewById(R.id.tv_screening_room);
        seatsView = findViewById(R.id.tv_seats);
    }

    // Populate UI with booking details.
    private void populateBookingDetails(Booking booking) {
        Screening screening = booking.getScreening();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String date = dateFormat.format(screening.getTime());
        String time = timeFormat.format(screening.getTime());
        String roomId = screening.getRoomID();
        List<String> seats = new ArrayList<>();

        for (Seat s : booking.getSeatList()) {
            seats.add(s.getSeatNum());
        }

        movieTitle.setText("Mausulut Atier");
        movieDetails.setText("2D Subtitles | Horror | 109 Minutes");
        screeningDate.setText("Screening Date: " + date);
        screeningTime.setText("Screening Time: " + time);
        screeningRoom.setText("Screening Room: " + roomId);
        seatsView.setText("Seats: " + String.join(", ", seats));
    }

    // Set up RecyclerView with ComboAdapter.

    private void setupRecyclerView(List<Combo> comboList, float ticketPrice) {
        ComboAdapter adapter = new ComboAdapter(this, comboList, ticketPrice, this::updateAmountViews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    // Update total amount and amount to pay views.

    private void updateAmountViews(double totalAmount) {
        double discount = calculateDiscount(totalAmount);
        double amountToPay = totalAmount - discount;

        tvTotalAmount.setText(String.format("Total Amount: %.2f $", totalAmount));
        tvAmountToPay.setText(String.format("Amount to Pay: %.2f $", amountToPay));
    }

    // Calculate discount based on total amount.
    private double calculateDiscount(double totalAmount) {
        // Example discount: 10% if total > 100
        return totalAmount > 100 ? totalAmount * 0.10 : 0;
    }

    //Generate a dummy list of combos for demonstration.

    private List<Combo> getCombos() {
        return Arrays.asList(
                new Combo("Beta Combo 69oz", "1 Bắp + 1 Nước có gaz", 2.8, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Family Combo 69oz", "2 Bắp + 2 Nước + 2 Snack", 9.5, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Sweet Combo 69oz", "1 Bắp + 2 Nước", 4.6, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png")
        );
    }
}
