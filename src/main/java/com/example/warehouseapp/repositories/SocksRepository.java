package com.example.warehouseapp.repositories;

import com.example.warehouseapp.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long>{

    List<Socks> findSocksByColorAndCottonPartGreaterThan(String color, int cottonPart);
    List<Socks> findSocksByColorAndCottonPartLessThan(String color, int cottonPart);
    List<Socks> findSocksByColorAndCottonPart(String color, int cottonPart);


}
