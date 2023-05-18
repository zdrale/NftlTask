package com.example.demo.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalTime;

public class TrainerAvailabilityDTO {
    private String fullName;
    private LocalTime availableAt;
    private LocalTime availableUntil;
    private String dayOfTheWeek;

    public TrainerAvailabilityDTO() {

    }

    public LocalTime getAvailableAt(){
        return availableAt;
    }


    public void setAvailableAt(LocalTime availableAt) {
        this.availableAt = availableAt;
    }


    public LocalTime getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(LocalTime availableUntil) {
        this.availableUntil = availableUntil;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }
}