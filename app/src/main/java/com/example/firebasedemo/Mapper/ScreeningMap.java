package com.example.firebasedemo.Mapper;

import com.example.firebasedemo.DTO.ScreeningDTO;
import com.example.firebasedemo.Model.Screening;

public class ScreeningMap {
    public ScreeningMap() {
    }
    public Screening DTO_Object(ScreeningDTO dto){
        Screening obj = new Screening();
        obj.setTime(dto.getTime());
        obj.setMovieID(dto.getMovieID());
        obj.setRoomID(dto.getMovieID());
        return obj;
    }

    public ScreeningDTO Object_DTO(Screening obj){
        ScreeningDTO dto = new ScreeningDTO();
        dto.setMovieID(obj.getMovieID());
        dto.setTime(obj.getTime());
        dto.setRoomID(obj.getRoomID());
        return dto;
    }
}
