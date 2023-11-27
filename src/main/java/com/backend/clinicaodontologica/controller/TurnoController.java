package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/registrarTurnos")
    public ResponseEntity<TurnoSalidaDto> registrarTurno(@RequestBody @Valid TurnoEntradaDto turno){
        return new ResponseEntity<>(turnoService.registrarTurno(turno), HttpStatus.CREATED);
    }

    //PUT
    @PutMapping("/actualizarTurnos")
    public ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody TurnoModificacionEntradaDto turno) {
        return new ResponseEntity<>(turnoService.actualizarTurno(turno), HttpStatus.OK);
    }


    //GET
    @GetMapping("/buscarTurnoPorId/{id}")
    public ResponseEntity<TurnoSalidaDto> buscarTurnoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(turnoService.buscarTurnoPorId(id), HttpStatus.OK);
    }


    //DELETE
    @DeleteMapping("eliminarTurnos/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurnoPorId(id);
        return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.OK);
    }

    //GET
    @GetMapping("/listarTurnos")
    public ResponseEntity<List<TurnoSalidaDto>> listarTodosLosTurnos() {
        List<TurnoSalidaDto> turnos = turnoService.listarTurnos();
        return new ResponseEntity<>(turnos, HttpStatus.OK);
    }
}
