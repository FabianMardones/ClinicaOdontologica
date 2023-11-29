package com.backend.clinicaodontologica.repository;

import com.backend.clinicaodontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnoRespository extends JpaRepository<Turno, Long> {
    List<Turno> findByPacienteId(Long pacienteId);
    List<Turno> findByOdontologoId(Long odontologoId);
}
