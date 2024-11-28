package com.example.firebasedemo.Activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.Adapter.ComboAdapter;
import com.example.firebasedemo.Api.CreateOrder;
import com.example.firebasedemo.Model.Booking;
import com.example.firebasedemo.Model.Combo;
import com.example.firebasedemo.Model.Screening;
import com.example.firebasedemo.Model.Seat;
import com.example.firebasedemo.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {

    // UI Components
    private TextView tvTotalAmount, tvAmountToPay, movieTitle, movieDetails, screeningDate, screeningTime, screeningRoom, seatsView;
    private Button payButton;
    private RecyclerView recyclerView;
    // Data
    private List<Combo> comboList;
    private Double amountToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize UI Components
        initUI();

        // Get data from Intent
        Booking booking = getIntent().getParcelableExtra("booking_data");
        Log.d("Tag", "Received booking: " + booking);

        // Populate UI with booking details.
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
        screeningDate.setText("Date: " + date);
        screeningTime.setText("Time: " + time);
        screeningRoom.setText("Room: " + roomId);
        seatsView.setText("Seats: " + String.join(", ", seats));


        // Initialize combo list and set up adapter
        comboList = getCombos();
        setupRecyclerView(comboList, booking.getPrice());

        // Zalo Payment handling
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String totalString = String.format("%.0f", amountToPay);

                //Handle paying order
                CreateOrder orderApi = new CreateOrder();
                try {
                    // Khi ấn thanh toán -> genrate ra code
                    JSONObject data = orderApi.createOrder(totalString);
                    String code = data.getString("return_code");

                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Toast.makeText(PaymentActivity.this, "Payement success", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Toast.makeText(PaymentActivity.this, "Payement cancle", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Toast.makeText(PaymentActivity.this, "Payement error", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
        payButton = findViewById(R.id.btn_pay);
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
        amountToPay = totalAmount - discount;

        tvTotalAmount.setText(String.format("Total Amount: %.2f vnd", totalAmount));
        tvAmountToPay.setText(String.format("Amount to Pay: %.2f vnd", amountToPay));
    }

    // Calculate discount based on total amount.
    private double calculateDiscount(double totalAmount) {
        // Example discount: 10% if total > 100
        return totalAmount > 100 ? totalAmount * 0.10 : 0;
    }

    //Generate a dummy list of combos for demonstration.
    private List<Combo> getCombos() {
        return Arrays.asList(
                new Combo("Beta Combo 69oz", "1 Bắp + 1 Nước có gaz", 100000, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Family Combo 69oz", "2 Bắp + 2 Nước + 2 Snack", 200000, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png"),
                new Combo("Sweet Combo 69oz", "1 Bắp + 2 Nước", 150000, "https://i.ibb.co/KztS0jw/Fun-One-Combo.png")
        );
    }
}
