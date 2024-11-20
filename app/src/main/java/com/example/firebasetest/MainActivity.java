package com.example.firebasetest;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.Adapter.ItemDateAdapter;
import com.example.firebasetest.Adapter.ItemTimeAdapter;
import com.example.firebasetest.Model.Screening;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity{

    private FirebaseFirestore db;
    private List<Screening> screenings;

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
        } catch (ParseException e) {
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

        // nhồi date + time vào recycle view
        RecyclerView dateRecyclerView = findViewById(R.id.dateRecycleView);
        RecyclerView timeRecyclerView = findViewById(R.id.timeRecycleView);

        List<String> dateList = new ArrayList<>(); dateList.addAll(slotSet.keySet());
        List<String> timeList = new ArrayList<>(); timeList.addAll(slotSet.get(dateList.get(0)));

        ItemDateAdapter dateAdapter = new ItemDateAdapter(this, dateList);
        ItemTimeAdapter timeAdapter = new ItemTimeAdapter(this, timeList);

        dateRecyclerView.setAdapter(dateAdapter);
        timeRecyclerView.setAdapter(timeAdapter);

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

}