package com.backend.clinicaodontologica.repository;

import com.backend.clinicaodontologica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByDni(int dni);
}
