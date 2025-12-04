package com.example.demo.respository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Integer> {
    List<Registro> findByUsuario_MatriculaAndFechaBetween(Integer matricula, LocalDate fechaInicio, LocalDate fechaFin);
    List<Registro> findByUsuario_MatriculaAndFechaBetweenAndTipoRegistro(Integer matricula, LocalDate fechaInicio, LocalDate fechaFin, String tipoRegistro);
    List<Registro> findTop3ByDispositivo_IdDispositivoOrderByFechaDescHoraDesc(Integer idDispositivo);
    List<Registro> findByDispositivo_IdDispositivoOrderByFechaDescHoraDesc(Integer idDispositivo);
    List<Registro> findByUsuario_MatriculaAndFecha(Integer matricula, LocalDate fecha);
}
