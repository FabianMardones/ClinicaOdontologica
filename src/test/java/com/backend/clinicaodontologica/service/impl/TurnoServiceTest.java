package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;

    @Test
    void deberiaLanzarUnaIllegalArgumentExceptionPorqueNoDebePermitirELRegistroDeUnTurnoSiNoHayUnPacienteYUnOdontologoRegistradosPreviamente(){
        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023,11,30,10,00), "Fabian", "Mardones", 186146611, "Fabian", "Mardones", "186146611");

        assertThrows(IllegalArgumentException.class, () -> turnoService.registrarTurno(turnoEntradaDto));
    }

    @Test
    void deberiaDarUnaListaVaciaAlMomentoDeConsultarPorUnListadoDeTurnosAlNoTenerNingunRegistro(){
        List<TurnoSalidaDto> turnoSalidaDto = turnoService.listarTurnos();

        assertTrue(turnoSalidaDto.isEmpty());
    }


    @Test
    void deberiaLanzarUnResourceNotFoundExceptionAlIntentarEliminarUnTurnoConElId1QueNoEstaRegistrado() {
        assertThrows(ResourceNotFoundException.class, () -> turnoService.eliminarTurnoPorId(1L));
    }

}