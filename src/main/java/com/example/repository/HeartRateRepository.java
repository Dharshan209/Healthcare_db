package com.example.repository;

import com.example.model.HeartRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HeartRateRepository extends JpaRepository<HeartRate, Long> {
    List<HeartRate> findByPatientId(Long patientId);
}
