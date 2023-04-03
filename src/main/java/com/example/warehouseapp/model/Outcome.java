package com.example.warehouseapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_outcome")
    private LocalDateTime dateOutcome;
    @OneToOne
    @JoinColumn(name = "socks_id")
    private Socks socksId;
}
