package com.example.warehouseapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_income")
    private LocalDateTime dateIncome;
    @OneToOne
    @JoinColumn(name = "socks_id")
    private Socks socksId;
}
