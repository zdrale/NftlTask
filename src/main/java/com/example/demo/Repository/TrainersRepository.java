package com.example.demo.Repository;

import com.example.demo.Model.TrainerModel;
import com.example.demo.dto.TrainerAvailabilityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainersRepository extends JpaRepository<TrainerModel,Long> {
    @Query(value = "SELECT DISTINCT t.fullName FROM TrainerModel t")
    List<String> findByName();

    @Query(value = "SELECT t FROM TrainerModel t WHERE t.fullName = :trainerName")
    List<TrainerModel> findTrainerAvailability(@Param("trainerName") String trainerName);

}
