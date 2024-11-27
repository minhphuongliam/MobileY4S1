package com.example.firebasedemo.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedemo.Adapter.ItemDateAdapter;
import com.example.firebasedemo.Adapter.ItemTimeAdapter;
import com.example.firebasedemo.Adapter.SeatAdapter;
import com.example.firebasedemo.Model.Booking;
import com.example.firebasedemo.Model.Screening;
import com.example.firebasedemo.Model.Seat;
import com.example.firebasedemo.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BookingActivity extends AppCompatActivity{

    private FirebaseFirestore db;
    private Map<String, Screening> screenings;
    private List<Seat> seats;

    private String userid;
    private String movieid;
    private Float totalCost = 0.0f;
    private int totalSeat = 0;
    private String selectedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Lấy movie, user id từ intent
        movieid = "1263992";
        userid = "ymllLaRQpoTur6rM3lfwRDnoZZ43";


        // Lấy data từ Firestore
        db = FirebaseFirestore.getInstance();
        db.collection("screenings")
                .whereEqualTo("movieID", movieid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        QuerySnapshot querySnapshots =task.getResult();
                        if(querySnapshots != null){

                            // Tạo danh sách để lưu screenings
                            Map<String, Screening> screenings = new HashMap<>();    //danh sách screening qua map để dễ tìm
                            Map<String, Set<String>> slotSet = new HashMap<>();     //danh sách time slot chiếu

                            // Format ngày và giờ
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat onlyDateSDF = new SimpleDateFormat("dd/MM/yyyy");
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat onlyTimeSDF = new SimpleDateFormat("HH:mm");

                            for(QueryDocumentSnapshot document : querySnapshots){
                                String screendingID = document.getId();
                                String movieID      = (String) document.get("movieID");
                                String roomID       = (String) document.get("roomID");
                                Timestamp dbTime    = document.getTimestamp("time");

                                // Chuyển timestamp thành Date
                                Date time = dbTime != null ? dbTime.toDate() : new Date();

                                String date = onlyDateSDF.format(time).trim();
                                String timeStr = onlyTimeSDF.format(time).trim();

                                screenings.put(date + " " + timeStr, new Screening(screendingID, movieID, roomID, time));
                                //Put time into sime slot map
                                if (!slotSet.containsKey(date)) {
                                    slotSet.put(date, new HashSet<>());
                                }
                                Objects.requireNonNull(slotSet.get(date)).add(timeStr);
                            }
                            // Sau khi hoàn tất việc lấy dữ liệu từ Firebase, xử lý phần còn lại
                            runOnUiThread(() -> initializeUI(slotSet, screenings));
                        }
                    }else{
                        Toast.makeText(this, "Fetch error:" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //Function to put itemdate & time in recycleview
    private void initializeUI(Map<String, Set<String>> slotSet, Map<String, Screening> screenings) {
        // Danh sách ngày và giờ
        List<String> dateList = new ArrayList<>(slotSet.keySet());
        sortDateList(dateList); // Hàm sắp xếp theo ngày (cần viết riêng)

        List<String> timeList = new ArrayList<>();


        // Khởi tạo RecyclerViews
        RecyclerView dateRecyclerView = findViewById(R.id.dateRecycleView);
        RecyclerView timeRecyclerView = findViewById(R.id.timeRecycleView);
        RecyclerView seatRecyclerView = findViewById(R.id.seatRecyclerView);

        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        timeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        seatRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));

        // Adapter cho danh sách ngày
        ItemDateAdapter dateAdapter = new ItemDateAdapter(this, dateList);
        dateRecyclerView.setAdapter(dateAdapter);

        // Xử lý chọn ngày
        dateAdapter.setOnItemSelectedListener((date, datePos) -> {
            seatRecyclerView.setAdapter(null);
            timeList.clear();
            timeList.addAll(slotSet.get(date));
            sortTimeList(timeList); // Hàm sắp xếp theo giờ (cần viết riêng)

            // Adapter cho danh sách giờ
            ItemTimeAdapter timeAdapter = new ItemTimeAdapter(this, timeList);
            timeRecyclerView.setAdapter(timeAdapter);

            // Xử lý chọn giờ
            timeAdapter.setOnItemSelectedListener((time, timePos) -> {
                selectedDateTime = date + " " + time;
                Screening selectedScreening = screenings.get(selectedDateTime);

                //xử lý ghế dựa trên screening được chọn
                db.collection("movieRoom")
                        .whereEqualTo("screeningID",selectedScreening.getId())
                        .get()
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()) {
                                QuerySnapshot querySnapshots = task.getResult();
                                List<Seat> seats = new ArrayList<>();

                                if (querySnapshots != null) {
                                    // Duyệt qua các document trả về
                                    for (QueryDocumentSnapshot document : querySnapshots) {
                                        // Lấy dữ liệu "seats" dưới dạng Map
                                        Map<String, String> seatMap = (Map<String, String>) document.get("seats");

                                        if (seatMap != null) {
                                            // Chuyển từng entry trong Map thành đối tượng Seat và thêm vào danh sách
                                            for (Map.Entry<String, String> entry : seatMap.entrySet()) {
                                                String seatNum = entry.getKey();
                                                String status = entry.getValue();
                                                seats.add(new Seat(seatNum, status));
                                            }
                                        }
                                    }
                                    // Cập nhật adapter
                                    SeatAdapter seatAdapter = new SeatAdapter(this, seats);
                                    seatAdapter.setOnItemSelectedListener(new SeatAdapter.OnItemSeatSelectedListener() {
                                        // cập nhật giá.
                                        @Override
                                        public void onItemSelected(boolean stats) {
                                            if (stats){
                                                totalCost += 70000.0f;
                                                totalSeat ++;
                                            }else{
                                                totalCost -= 70000.0f;
                                                totalSeat --;
                                            }

                                            moneyUPD();
                                        }
                                    });
                                    seatRecyclerView.setAdapter(seatAdapter);
                                }
                            }else{
                                Toast.makeText(this, "Fetch error:" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });
            });
        });
        moneyUPD();

        // Xử lý nút booking (ví dụ)
        Button bookButton = findViewById(R.id.bookButton);
        bookButton.setOnClickListener(v -> {
            Screening selectingScreening = screenings.get(selectedDateTime);

            // Tạo booking mới
            List<Seat> holdingSeat = new ArrayList<>();
            for (Seat s : ((SeatAdapter) Objects.requireNonNull(seatRecyclerView.getAdapter())).getSeatList() ) {
                if(s.getStatus() == Seat.Status.TAPPING){
                    holdingSeat.add(s);
                }
            }
            Booking booking = new Booking(
                    "",
                    userid,
                    selectingScreening,
                    holdingSeat,
                    new Date() /*date lúc sau tuấn lại đặt lại chăng?*/,
                    totalCost,
                    new ArrayList<>(),
                    false
            );
            //Nhay sang trang sau
            Intent intent = new Intent(this, NextoActivity.class);
            intent.putExtra("booking_data", (Parcelable) booking);
            startActivity(intent);
        });
    }

    // function cập nhật tiền, ghế
    public void moneyUPD(){
        TextView totalSeattxt = findViewById(R.id.totalSeats);
        TextView totalCosttxt = findViewById(R.id.totalCost);

        String seatStr = "Total seats: " + totalSeat,
                cost = "Total cost: " + totalCost + "vnd";

        totalSeattxt.setText(seatStr);
        totalCosttxt.setText(cost);
    }

    // Function to sort the date list
    public static void sortDateList(List<String> dateList) {
        Collections.sort(dateList, new Comparator<String>() {
            SimpleDateFormat onlyDateSDF = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            public int compare(String date1, String date2) {
                try {
                    Date d1 = onlyDateSDF.parse(date1);
                    Date d2 = onlyDateSDF.parse(date2);
                    return d1.compareTo(d2); // Compare the dates
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }

    // Function to sort the time list
    public static void sortTimeList(List<String> timeList) {
        Collections.sort(timeList, new Comparator<String>() {
            SimpleDateFormat onlyTimeSDF = new SimpleDateFormat("HH:mm");
            @Override
            public int compare(String time1, String time2) {
                try {
                    Date t1 = onlyTimeSDF.parse(time1);
                    Date t2 = onlyTimeSDF.parse(time2);
                    return t1.compareTo(t2); // Compare the times
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }

    // Function to sort the seats list
    public static void sortSeatsByNumber(List<Seat> seats) {
        Collections.sort(seats, new Comparator<Seat>() {
            @Override
            public int compare(Seat s1, Seat s2) {
                // Chuyển seatNum từ String sang Integer để so sánh
                int num1 = Integer.parseInt(s1.getSeatNum());
                int num2 = Integer.parseInt(s2.getSeatNum());
                return Integer.compare(num1, num2);
            }
        });
    }

}