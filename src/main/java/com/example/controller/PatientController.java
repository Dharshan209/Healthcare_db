package com.example.controller;

import com.example.model.Patient;
import com.example.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;



@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@Tag(name = "Patient Management", description = "Endpoints for managing patients")
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    @Operation(summary = "Add a new patient")
    public ResponseEntity<?> addPatient(@Valid @RequestBody Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Patient savedPatient = patientService.addPatient(patient);
        return ResponseEntity.ok(savedPatient);
    }
    

    @GetMapping("/user/{userId}")
     @Operation(summary = "Get all patients for a user")
    public ResponseEntity<List<Patient>> getPatientsByUserId(@PathVariable Long userId) {
        List<Patient> patients = patientService.getPatientsByUserId(userId);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
     @Operation(summary = "Get patient by ID")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
