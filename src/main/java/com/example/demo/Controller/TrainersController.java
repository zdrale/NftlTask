package com.example.demo.Controller;

import com.example.demo.Model.TrainerModel;
import com.example.demo.Repository.TrainersRepository;
import com.example.demo.dto.TrainerAvailabilityDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TrainersController {
    @Autowired
    private TrainersRepository trainersRepository;

    private final ModelMapper modelMapper;
    @Autowired
    public TrainersController(TrainersRepository trainersRepository, ModelMapper modelMapper) {
        this.trainersRepository = trainersRepository;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/trainers")
    public List<String> getTrainers() {
        return trainersRepository.findByName();
    }

    @GetMapping("availability/{trainerName}")
    public ResponseEntity<List<TrainerAvailabilityDTO>> getTrainerAvailability(@PathVariable String trainerName) {
        final ZonedDateTime localDateTime = LocalDateTime.now().atOffset(ZoneOffset.of("-08:00")).toZonedDateTime();
        final DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        List<TrainerModel> trainers = trainersRepository.findTrainerAvailability(trainerName);

        List<TrainerAvailabilityDTO> availabilityDTOS = trainers.stream()
                .filter(trainer -> trainer.getDayOfTheWeek().equalsIgnoreCase(dayOfWeek.toString()))
                .flatMap(trainer -> createTimeSlots(LocalTime.now(), trainer.getAvailableAt(), trainer.getAvailableUntil()))
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(availabilityDTOS);
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

//od trenutka poziva apija
