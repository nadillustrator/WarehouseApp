package com.example.warehouseapp.services;

import com.example.warehouseapp.exceptions.InvalidCottonPartException;
import com.example.warehouseapp.exceptions.InvalidRequestException;
import com.example.warehouseapp.exceptions.NotFoundException;
import com.example.warehouseapp.exceptions.QuantityIsNotEnoughException;
import com.example.warehouseapp.model.Socks;
import com.example.warehouseapp.repositories.SocksRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocksService {

    private final SocksRepository socksRepository;

    public SocksService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }


    public Socks findById(Long id) {
        return socksRepository.findById(id).orElse(null);
    }

    public Socks addSocks(Socks socks) {
        int cottonPart = socks.getCottonPart();
        if ( cottonPart > 100 || cottonPart < 0) {
            throw new InvalidCottonPartException();
        }
        Socks socksDB = socksRepository.findById(socks.getId()).orElse(null);
        if(socksDB == null) {
            return socksRepository.save(socks);
        }
        if(!socks.getColor().equals(socksDB.getColor()) || cottonPart != socksDB.getCottonPart()) {
            throw new InvalidRequestException();
        }
        int sum = socks.getQuantity() + socksDB.getQuantity();
        socks.setQuantity(sum);
        return socksRepository.save(socks);
    }

    public Socks outcomeSocks(Socks socks) throws NotFoundException, QuantityIsNotEnoughException {
        int outcome = socks.getQuantity();
        System.out.println(outcome);
        Socks socksAvailability = socksRepository.findById(socks.getId()).orElse(null);
        System.out.println(socksAvailability);
        if (socksAvailability == null) {
            throw new NotFoundException();
        }
        int quantity = socksAvailability.getQuantity();
        System.out.println(quantity);
        int balance = quantity - outcome;
        System.out.println(balance);
        socksAvailability.setQuantity(balance);
        if (balance == 0) {
            return socksRepository.save(socksAvailability);
        } else if (balance < 0) {
            throw new QuantityIsNotEnoughException();
        }
        return socksRepository.save(socksAvailability);
    }
}
