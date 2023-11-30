package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.exceptions.DniDuplicadoException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaRegistrarUnPacienteDeNombreFabianYRetornarElId(){
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Fabian", "Mardones", 186146611, LocalDate.of(2023,12,24), new DomicilioEntradaDto("Baquedano", 1100, "San Bernardo", "Maipo"));

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        assertNotNull(pacienteSalidaDto.getId());
        assertEquals("Fabian", pacienteSalidaDto.getNombre());
    }

    @Test
    @Order(2)
    void deberiaLanzarUnaExceptionDniDuplicadoExceptionCuandoSeRegistraUnPacienteConElMismoDni(){

        PacienteEntradaDto pacienteEntradaDto1 = new PacienteEntradaDto("Esteban", "Zapata", 186146611, LocalDate.of(2023, 12, 24), new DomicilioEntradaDto("Baquedano", 1100, "San Bernardo", "Maipo"));

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto1);

        assertThrows(DniDuplicadoException.class, () -> pacienteService.registrarPaciente(pacienteEntradaDto1));
    }

    @Test
    @Order(3)
    void deberiaEliminarElPacienteConId1YaEliminado_deberiaLanzarUnaResourceNotFoundException(){
        try{
            pacienteService.eliminarPacientePorId(1L);
        }catch (Exception exception){
            exception.printStackTrace();
        }

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.eliminarPacientePorId(1L));
    }
}