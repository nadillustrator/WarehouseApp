package com.example.warehouseapp.repositories;

import com.example.warehouseapp.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long>{

    Optional<Socks> findById(Long id);

//    List<Socks> findSocksByColorAndCottonPartMoreThan(String color, byte cottonPart);
//    List<Socks> findSocksByColorAndCottonPartLessThan(String color, byte cottonPart);
//
//    List<Socks> findSocksByColorAndCottonPart(String color, byte cottonPart);


}
