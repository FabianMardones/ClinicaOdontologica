package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    void deberiaDevolverUnaListaVaciaDeOdontologos(){

        List<OdontologoSalidaDto> odontologos = odontologoService.listarOdontologos();

        assertTrue(odontologos.isEmpty());
    }


    @Test
    void deberiaFallarEnLaBusquedaDelOdontologoId1_yaQueNoExiste(){
        OdontologoSalidaDto odontologo = odontologoService.buscarOdontologoPorId(1L);

        assertNull(odontologo, "Se esperaba que el paciente con ID 2 no existiera");
    }

    @Test
    void deberiaRegistrarUnOdontologoDeNombreEstebanYApellidoMardonesAdemasDeRetonarElId_yNoLanzarNingunaException(){
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("Esteban", "Mardones", "186146611");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertEquals("Esteban", odontologoSalidaDto.getNombre());
        assertEquals("Mardones", odontologoSalidaDto.getApellido());
        assertNotNull(odontologoSalidaDto.getId());
    }
}