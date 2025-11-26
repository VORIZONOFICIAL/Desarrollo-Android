package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Calendario;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Integer> {
}
