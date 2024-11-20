package com.example.firebasetest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firebasetest.Model.Screening;
import com.example.firebasetest.Model.TimeDate;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity{

    private FirebaseFirestore db;
    private Vector<Screening> screenings;

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

        Map<String,TimeDate> slotSet = new HashMap<>();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for(Screening screening : screenings){
            String[] t = sdf.format(screening.getTime()).trim().split(" ");
            
        }

    }
}