package com.example.demo.service;

import com.example.demo.dto.TrainerAvailabilityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TrainerService {
    ResponseEntity<List<TrainerAvailabilityDTO>> getTrainerAvailability(@PathVariable String trainerName);
    List<String> getTrainers();

}
