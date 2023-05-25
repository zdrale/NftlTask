package com.example.demo.Controller;
import com.example.demo.dto.TrainerAvailabilityDTO;
import com.example.demo.service.TrainerService;
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
public class TrainersController  {
    private final TrainerService trainerService;

    public TrainersController(TrainerService trainerService){
        this.trainerService = trainerService;
    }

    @GetMapping("/trainers")
    public List<String> getTrainers() {
        return trainerService.getTrainers();
    }

    @GetMapping("availability/{trainerName}")
    public ResponseEntity<List<TrainerAvailabilityDTO>> getTrainerAvailability(@PathVariable String trainerName) {
       return trainerService.getTrainerAvailability(trainerName);
    }



}


