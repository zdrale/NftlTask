package com.example.demo.Model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name="trainers")
public class TrainerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="full_name")
    private String fullName;
    @Column(name="timezone")
    private String timezone;
    @Column(name="day_of_the_week")
    private String dayOfTheWeek;
    @Column(name="available_at")
    private LocalTime availableAt;
    @Column(name="available_untill")
    private LocalTime availableUntil;
    public TrainerModel(){}

    public TrainerModel(String fullName, String timezone, String dayOfTheWeek, LocalTime availableAt, LocalTime availableUntil) {
        super();
        this.fullName = fullName;
        this.timezone = timezone;
        this.dayOfTheWeek = dayOfTheWeek;
        this.availableAt = availableAt;
        this.availableUntil = availableUntil;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public LocalTime getAvailableAt() {
        return availableAt;
    }

    public LocalTime getAvailableUntil() {
        return availableUntil;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public void setAvailableAt(LocalTime availableAt) {
        this.availableAt = availableAt;
    }

    public void setAvailableUntil(LocalTime availableUntil) {
        this.availableUntil = availableUntil;
    }
}
