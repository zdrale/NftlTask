package com.example.demo.service;

import com.example.demo.Model.TrainerModel;
import com.example.demo.Repository.TrainersRepository;
import com.example.demo.dto.TrainerAvailabilityDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrainerServiceImplementation implements TrainerService {
    private final TrainersRepository trainersRepository;
    public TrainerServiceImplementation(TrainersRepository trainersRepository){
        this.trainersRepository = trainersRepository;
    }

    @Override
    public ResponseEntity<List<TrainerAvailabilityDTO>> getTrainerAvailability(@PathVariable String trainerName){
        try {
            List<TrainerModel> trainers = trainersRepository.findTrainerAvailability(trainerName);
            final ZonedDateTime localDateTime = ZonedDateTime.now();
            final DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

            List<TrainerAvailabilityDTO> availabilityDTOS = trainers.stream()
                    .filter(trainer -> trainer.getDayOfTheWeek().equalsIgnoreCase(dayOfWeek.toString()))
                    .flatMap(trainer -> createTimeSlots(LocalTime.now(), trainer.getAvailableAt(), trainer.getAvailableUntil()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok()
                    .body(availabilityDTOS);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error occurred", e);
        }
    }

    @Override
    public List<String> getTrainers() {
        return trainersRepository.findTrainersNames();
    }

    private Stream<TrainerAvailabilityDTO> createTimeSlots(LocalTime currentTime, LocalTime startTime, LocalTime endTime) {
        List<TrainerAvailabilityDTO> timeSlots = new ArrayList<>();

        while (currentTime.isBefore(endTime) && currentTime.plusMinutes(30).isBefore(endTime)) {
            TrainerAvailabilityDTO dto = new TrainerAvailabilityDTO();
            dto.setAvailableAt(currentTime.truncatedTo(ChronoUnit.MINUTES));
            currentTime = currentTime.plusMinutes(30);
            dto.setAvailableUntil(currentTime.truncatedTo(ChronoUnit.MINUTES));
            timeSlots.add(dto);
        }

        return timeSlots.stream();
    }
}


