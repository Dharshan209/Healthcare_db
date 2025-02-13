package com.example.service;

import com.example.model.HeartRate;
import com.example.repository.HeartRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HeartRateService {

    @Autowired
    private HeartRateRepository heartRateRepository;

    public HeartRate addHeartRate(HeartRate heartRate) {
        return heartRateRepository.save(heartRate);
    }

    public List<HeartRate> getHeartRatesByPatientId(Long patientId) {
        return heartRateRepository.findByPatientId(patientId);
    }
}
