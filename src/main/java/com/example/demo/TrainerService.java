package com.example.demo;

import com.example.demo.Model.TrainerModel;
import com.example.demo.dto.TrainerAvailabilityDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {
    @Autowired
    private ModelMapper modelMapper;

    public TrainerAvailabilityDTO mapTrainerToAvailabilityDto(TrainerModel trainer) {
        return modelMapper.map(trainer, TrainerAvailabilityDTO.class);
    }
}
