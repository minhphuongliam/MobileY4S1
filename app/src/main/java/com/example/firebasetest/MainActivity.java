package com.example.firebasetest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.Adapter.ItemDateAdapter;
import com.example.firebasetest.Adapter.ItemTimeAdapter;
import com.example.firebasetest.Adapter.SeatAdapter;
import com.example.firebasetest.Model.Screening;
import com.example.firebasetest.Model.Seat;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity{

    private FirebaseFirestore db;
    private List<Screening> screenings;
    private List<Seat> seats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//Bôi để test chức năng cơ bản bằng dummy object
/*
        //lấy movie id từ cái intent
        String movieid = "123";
        db = FirebaseFirestore.getInstance();

        db.collection("sample")
                .whereEqualTo("movieID",movieid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        // nhồi data vào screenings
                        QuerySnapshot querySnapshots =task.getResult();
                        if(querySnapshots != null){
                            for(QueryDocumentSnapshot document : querySnapshots){
                                String screendingID = document.getId();
                                String movieID      = (String) document.get("movieID");
                                String roomID       = (String) document.get("roomID");
                                Timestamp dbTime    = document.getTimestamp("time");

                                // convert time to DateTime:
                                Date time = new Date();
                                if(dbTime != null){
                                    time = dbTime.toDate();
                                }

                                screenings.add(new Screening(screendingID, movieID, roomID, time));
                            }
                        }
                    }else{
                        Toast.makeText(this, "Fetch error:" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
*/

        //Dummy
        try {
            screenings = dummymaker();
            seats = dummySeat(7);
        } catch (ParseException e) {
            Toast.makeText(MainActivity.this, "Kong load dc data!", Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }

        //

        //lọc các mốc thời gian của screening ra
        Map<String, Set<String>> slotSet = new HashMap<>();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat onlyDateSDF = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat onlyTimeSDF = new SimpleDateFormat("HH:mm");

        for(Screening screening : screenings){
            String date = onlyDateSDF.format(screening.getTime()).trim();
            String time = onlyTimeSDF.format(screening.getTime()).trim();
            if(slotSet.containsKey(date)){
                slotSet.get(date).add(time);
            }else{
                slotSet.put(date,new HashSet<>());
                slotSet.get(date).add(time);
            }
        }
        List<String> dateList = new ArrayList<>(); dateList.addAll(slotSet.keySet());
        List<String> timeList = new ArrayList<>(); timeList.addAll(slotSet.get(dateList.get(0)));

        sortDateList(dateList);
        sortTimeList(timeList);

        // nhồi date + time vào recycle view code test chuc nang
        RecyclerView dateRecyclerView = findViewById(R.id.dateRecycleView);
        RecyclerView timeRecyclerView = findViewById(R.id.timeRecycleView);

        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        timeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ItemDateAdapter dateAdapter = new ItemDateAdapter(this, dateList);
        ItemTimeAdapter timeAdapter = new ItemTimeAdapter(this, timeList);

        dateRecyclerView.setAdapter(dateAdapter);
        timeRecyclerView.setAdapter(timeAdapter);

        //nhoi seat vao test chuc nang
        RecyclerView seatRecyclerView = findViewById(R.id.seatRecyclerView);
//        Toast.makeText(MainActivity.this, "Khong co seat" + seats.size(), Toast.LENGTH_LONG).show();
        SeatAdapter seatAdapter = new SeatAdapter(this, seats);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);

        seatRecyclerView.setAdapter(seatAdapter);
        seatRecyclerView.setLayoutManager(gridLayoutManager);
    }

    // sample dummy generator
    public List<Screening> dummymaker() throws ParseException {
        List<Screening> screenings = new ArrayList<>();

        // Define the date range
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat onlyDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = onlyDateFormat.parse("01/12/2024");
        Date endDate = onlyDateFormat.parse("20/12/2024");

        // Define times for each day
        String[] times = {"08:30", "10:00", "13:00", "15:00", "17:30", "18:00", "20:00", "21:00"};

        // Generate dates between start and end
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        int idCounter = 1; // For unique ID generation
        while (!calendar.getTime().after(endDate)) {
            String currentDate = onlyDateFormat.format(calendar.getTime());

            // Generate screenings for the current day
            for (String time : times) {
                String dateTime = currentDate + " " + time;
                Date screeningDateTime = dateFormat.parse(dateTime);

                // Create a Screening object
                Screening screening = new Screening(
                        "S" + idCounter++, // Unique ID
                        "Movie" + idCounter, // Movie ID
                        "Room" + idCounter, // Room ID
                        screeningDateTime
                );

                screenings.add(screening);
            }

            // Move to the next day
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return screenings;
        // Print the generated screenings
//        for (Screening screening : screenings) {
//            System.out.println("ID: " + screening.id +
//                    ", MovieID: " + screening.movieID +
//                    ", RoomID: " + screening.roomID +
//                    ", Time: " + dateFormat.format(screening.time));
//        }
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

    // Function to make Seats dummy
    public static List<Seat> dummySeat(int n) {
        List<Seat> seats = new ArrayList<>();
        char row = 'A';  // Row bắt đầu từ A
        int seatCount = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String seatNum = row + String.valueOf(seatCount);  // Ví dụ: A1, A2, B1, B2, ...
                Seat.Status status = Seat.Status.values()[(i + j) % 4];  // Chọn trạng thái vòng từ AVAILABLE đến BOOKED

                seats.add(new Seat(seatNum, status.name().toLowerCase()));
                seatCount++;
            }
            row++;  // Di chuyển sang hàng tiếp theo
            seatCount = 1;  // Reset số ghế cho hàng mới
        }

        return seats;
    }
}