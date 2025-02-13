package com.example.service;

import com.example.model.Patient;
import com.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getPatientsByUserId(Long userId) {
        return patientRepository.findByUserId(userId);
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
}
