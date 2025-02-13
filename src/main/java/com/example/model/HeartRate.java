package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "heart_rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeartRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 30, message = "Heart rate must be at least 30 BPM")
    private int bpm;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
