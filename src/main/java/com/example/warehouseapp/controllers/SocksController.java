package com.example.warehouseapp.controllers;

import com.example.warehouseapp.model.Socks;
import com.example.warehouseapp.services.SocksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/socks")
public class SocksController {

    private static Logger logger = Logger.getLogger(SocksController.class.getName());

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Socks> findById(@PathVariable Long id) {
        return ResponseEntity.ok(socksService.findById(id));
    }

    @PostMapping("/income")
    public ResponseEntity<Socks> addSocks(@RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.addSocks(socks));
    }

    @PutMapping("/outcome")
    public ResponseEntity<Socks> outcomeSocks(@RequestBody Socks socks) {
        Socks editedSocks = socksService.outcomeSocks(socks);
        if (editedSocks == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editedSocks);
    }
}
