package com.backend.clinicaodontologica.repository;

import com.backend.clinicaodontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {
    @Query("SELECT o FROM Odontologo o WHERE o.matricula = :matricula")
    Odontologo findByMatricula(@Param("matricula") String matricula);
}
