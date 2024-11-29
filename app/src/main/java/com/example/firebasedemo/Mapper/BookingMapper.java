package com.example.firebasedemo.Mapper;

import com.example.firebasedemo.DTO.BookingDTO;
import com.example.firebasedemo.DTO.ComboDTO;
import com.example.firebasedemo.Model.Booking;
import com.example.firebasedemo.Model.Seat;
import com.example.firebasedemo.Model.Voucher;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {

    public static BookingDTO mapToBookingDTO(Booking booking, List<ComboDTO> comboDTOList){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUserID(booking.getUserID());
        bookingDTO.setScreening(booking.getScreening());
        bookingDTO.setSeatList(new ArrayList<>());
        for(Seat s : booking.getSeatList()){
            bookingDTO.getSeatList().add(s.getSeatNum());
        }
        bookingDTO.setComboList(comboDTOList);
        bookingDTO.setBookTime(booking.getBookTime());
        bookingDTO.setPrice(bookingDTO.getPrice());
        bookingDTO.setVouchers(new ArrayList<>());
        for(Voucher v : booking.getVouchers()){
            bookingDTO.getVouchers().add(v.getId());
        }
        bookingDTO.setPayed(booking.getPayed());

        return bookingDTO;
    }
}
