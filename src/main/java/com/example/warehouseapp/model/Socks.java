package com.example.warehouseapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;
    @Column(name = "cotton_part")
    private int cottonPart;
    private int quantity;


}
