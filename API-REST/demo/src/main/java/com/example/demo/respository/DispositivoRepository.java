package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Dispositivo;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {
}
