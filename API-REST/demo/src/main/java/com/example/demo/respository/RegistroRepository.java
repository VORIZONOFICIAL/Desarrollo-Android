package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Registro;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {
    List<Registro> findByUsuario_MatriculaAndFechaBetween(Integer matricula, LocalDate fechaInicio, LocalDate fechaFin);
    List<Registro> findByUsuario_MatriculaAndFechaBetweenAndTipoRegistro(Integer matricula, LocalDate fechaInicio, LocalDate fechaFin, String tipoRegistro);
}
