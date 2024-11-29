package com.example.firebasedemo.Mapper;

import com.example.firebasedemo.DTO.MovieRoomDTO;
import com.example.firebasedemo.Model.MovieRoom;
import com.example.firebasedemo.Model.Seat;

import java.util.HashMap;

public class MovieRoomMap {
    public MovieRoomMap(){}

    public MovieRoomDTO Object_DTO(MovieRoom obj){
        MovieRoomDTO dto = new MovieRoomDTO();

        dto.setScreeningID(obj.getScreening().getId());
        dto.setSeats(new HashMap<>());

        for (Seat s: obj.getSeats()) {
            dto.getSeats().put(s.getSeatNum(),s.getStatus().toString());
        }
        return dto;
    }
}