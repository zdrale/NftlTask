package com.example.demo.tests;

import com.example.demo.Model.TrainerModel;
import com.example.demo.Repository.TrainersRepository;
import com.example.demo.dto.TrainerAvailabilityDTO;
import com.example.demo.service.TrainerServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TrainerServiceImplementationTest {
    private TrainerServiceImplementation trainerService;

    @Mock
    private TrainersRepository trainersRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainerService = new TrainerServiceImplementation(trainersRepository);
    }

    @Test
    void getTrainerAvailability_ReturnsAvailabilityDTOList_WhenTrainerFound() {
      
        String trainerName = "John";
        List<TrainerModel> trainers = createMockTrainerList(trainerName);
        Mockito.when(trainersRepository.findTrainerAvailability(trainerName)).thenReturn(trainers);

        
        DayOfWeek currentDayOfWeek = DayOfWeek.MONDAY;
        ZonedDateTime now = ZonedDateTime.now().with(TemporalAdjusters.nextOrSame(currentDayOfWeek));

        
        ResponseEntity<List<TrainerAvailabilityDTO>> response = trainerService.getTrainerAvailability(trainerName);

        
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        
        List<TrainerAvailabilityDTO> availabilityDTOS = response.getBody();
        List<TrainerAvailabilityDTO> expectedAvailabilityDTOS = createTimeSlots(now.toLocalTime(), trainers.get(0).getAvailableAt(), trainers.get(0).getAvailableUntil()).collect(Collectors.toList());
        Assertions.assertEquals(expectedAvailabilityDTOS, availabilityDTOS);

    
        Mockito.verify(trainersRepository).findTrainerAvailability(trainerName);
    }

    @Test
    void getTrainerAvailability_ThrowsInternalServerError_WhenRepositoryThrowsException() {
        String trainerName = "John";
        Mockito.when(trainersRepository.findTrainerAvailability(trainerName)).thenThrow(new RuntimeException("Error occurred"));

        Assertions.assertThrows(ResponseStatusException.class, () -> trainerService.getTrainerAvailability(trainerName));

        Mockito.verify(trainersRepository).findTrainerAvailability(trainerName);
    }

  
    private List<TrainerModel> createMockTrainerList(String trainerName) {
        TrainerModel trainer = new TrainerModel();
        trainer.setFullName(trainerName);
        trainer.setDayOfTheWeek(DayOfWeek.MONDAY.toString());
        trainer.setTimezone(ZoneId.of("America/North_Dakota/New_Salem").toString());
        trainer.setAvailableAt(LocalTime.of(8, 0));
        trainer.setAvailableUntil(LocalTime.of(23, 0));

        List<TrainerModel> trainers = new ArrayList<>();
        trainers.add(trainer);

        return trainers;
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
