package com.example.controller;

import com.example.model.HeartRate;
import com.example.service.HeartRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@Tag(name = "Heart Rate Management", description = "Endpoints for managing heart rate records")
@RequestMapping("/api/heartrate")
public class HeartRateController {

    @Autowired
    private HeartRateService heartRateService;

    @PostMapping
    @Operation(summary = "Add a heart rate record")
    public ResponseEntity<?> addHeartRate(@Valid @RequestBody HeartRate heartRate, BindingResult result) {
    if (result.hasErrors()) {
        return ResponseEntity.badRequest().body(result.getAllErrors());
    }
    HeartRate savedHeartRate = heartRateService.addHeartRate(heartRate);
    return ResponseEntity.ok(savedHeartRate);
    }


    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get heart rate records for a patient")
    public ResponseEntity<List<HeartRate>> getHeartRatesByPatientId(@PathVariable Long patientId) {
        List<HeartRate> heartRates = heartRateService.getHeartRatesByPatientId(patientId);
        return ResponseEntity.ok(heartRates);
    }
}
