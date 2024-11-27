package com.example.firebasetest;

import com.example.firebasetest.Mapper.ScreeningMap;
import com.example.firebasetest.Model.Screening;
import com.example.firebasetest.Model.Seat;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class DummyPusher {
    public static boolean pushScreeningToDb(Screening screening){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ScreeningMap map = new ScreeningMap();
        AtomicBoolean res = new AtomicBoolean(false);

        db.collection("screenings")
            .add(map.Object_DTO(screening))
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Voucher added successfully");
                    res.set(true);
                })
                .addOnFailureListener(e -> System.err.println("Error adding voucher: " + e));

        return res.get();
    }

    // sample dummy generator
    public static List<Screening> dummymaker() throws ParseException {
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

        Random random = new Random();
        int idCounter = 1; // For unique ID generation

        while (!calendar.getTime().after(endDate)) {
            String currentDate = onlyDateFormat.format(calendar.getTime());

            // Generate screenings for the current day
            List<String> availableTimes = new ArrayList<>(Arrays.asList(times));

            // Randomly remove some times for the current day
            int timesToRemove = random.nextInt(availableTimes.size()); // Number of times to remove
            for (int i = 0; i < timesToRemove; i++) {
                int removeIndex = random.nextInt(availableTimes.size());
                availableTimes.remove(removeIndex);
            }

            // Add screenings for the remaining times
            for (String time : availableTimes) {
                String dateTime = currentDate + " " + time;
                Date screeningDateTime = dateFormat.parse(dateTime);

                // Create a Screening object
                Screening screening = new Screening(
                        "", // Unique ID
                        "1263992", // Movie ID
                        "2", // Room ID
                        screeningDateTime
                );

                screenings.add(screening);
            }

            // Move to the next day
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return screenings;
    }

    // Function to make Seats dummy
    public static List<Seat> dummySeat(int n) {
        List<Seat> seats = new ArrayList<>();
        int seatNumber = 1; // Bắt đầu từ 1
        Random random = new Random(); // Tạo một đối tượng Random

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Sử dụng số ghế là số tự nhiên tăng dần
                String seatNum = String.valueOf(seatNumber);


                /// Danh sách các trạng thái hợp lệ
                Seat.Status[] validStatuses = {
                        Seat.Status.UNAVAILABLE,
                        Seat.Status.AVAILABLE,
                        Seat.Status.BOOKED
                };

                // Tạo số ngẫu nhiên trong khoảng 0 đến (validStatuses.length - 1)
                int randomIndex = random.nextInt(validStatuses.length);

                // Lấy trạng thái ngẫu nhiên
                Seat.Status randomStatus = validStatuses[randomIndex];

                seats.add(new Seat(seatNum, randomStatus));
                seatNumber++; // Tăng số ghế
            }
        }

        return seats;
    }
}
