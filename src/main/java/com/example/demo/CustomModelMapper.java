package com.example.demo;

import com.example.demo.Model.TrainerModel;
import com.example.demo.dto.TrainerAvailabilityDTO;
import org.springframework.stereotype.Service;

@Service
public class CustomModelMapper {

    private  org.modelmapper.ModelMapper modelMapper;

    public TrainerAvailabilityDTO mapTrainerToAvailabilityDto(TrainerModel trainer) {
        return modelMapper.map(trainer, TrainerAvailabilityDTO.class);
    }
}
