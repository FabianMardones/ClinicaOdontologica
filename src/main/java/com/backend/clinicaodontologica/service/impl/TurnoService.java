package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.OdontologoRepository;
import com.backend.clinicaodontologica.repository.PacienteRepository;
import com.backend.clinicaodontologica.repository.TurnoRespository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(ITurnoService.class);
    private final TurnoRespository turnoRespository;
    private final ModelMapper modelMapper;
    private final OdontologoService odontologoService;
    private final PacienteService pacienteService;

    public TurnoService(TurnoRespository turnoRespository, ModelMapper modelMapper, OdontologoService odontologoService, PacienteService pacienteService) {
        this.turnoRespository = turnoRespository;
        this.modelMapper = modelMapper;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        configurarMapping();
    }

    @Transactional
    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoDto) {
        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turnoDto));

        LOGGER.info("DNI en TurnoEntradaDto: {}", turnoDto.getDni());
        LOGGER.info("Matrícula en TurnoEntradaDto: {}", turnoDto.getMatricula());

        validarFechaYhoraTurno(turnoDto.getFechaYHora());

        Paciente pacienteExistente = pacienteService.buscarPacientePorDni(turnoDto.getDni());
        Odontologo odontologoExistente = odontologoService.buscarOdontologoPorMatricula(turnoDto.getMatricula());

        Turno turnoEntidad = modelMapper.map(turnoDto, Turno.class);
        TurnoSalidaDto turnoSalidaDto = null;

        if (pacienteExistente != null && pacienteExistente.getId() != null && odontologoExistente != null && odontologoExistente.getId() != null) {
            turnoEntidad.setPaciente(pacienteExistente);
            turnoEntidad.setOdontologo(odontologoExistente);

            LOGGER.info("Paciente Existente: {}", JsonPrinter.toString(pacienteExistente));
            LOGGER.info("Odontologo Existente: {}", JsonPrinter.toString(odontologoExistente));


            Turno turnoAPersistir = turnoRespository.save(turnoEntidad);
            turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);

            LOGGER.info("DNI en Turno antes de persistir: {}", turnoEntidad.getPaciente().getDni());
            LOGGER.info("Matrícula en Turno antes de persistir: {}", turnoEntidad.getOdontologo().getMatricula());

            turnoSalidaDto.setDni(turnoAPersistir.getPaciente().getDni());
            turnoSalidaDto.setMatricula(turnoAPersistir.getOdontologo().getMatricula());

            LOGGER.info("Turnos Salida Dto: {}", JsonPrinter.toString(turnoSalidaDto));
        } else {
            LOGGER.error("No se han encontrado los pacientes y odontologos");
            throw new IllegalArgumentException("Error! no hay pacientes registrados para generar un turno");
        }
        return turnoSalidaDto;
    }


    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnoSalidaDto = turnoRespository.findAll().stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class)).toList();
        LOGGER.info("Listado de todos los turnos: {}", turnoSalidaDto);
        return turnoSalidaDto;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRespository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;
        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", turnoEncontrado);
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
        }
        return turnoEncontrado;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turnoModificacion) {
        validarFechaYhoraTurno(turnoModificacion.getFechaYHora());
        Long idTurno = turnoModificacion.getId();

        Optional<Turno> optionalTurno = turnoRespository.findById(idTurno);

        if (optionalTurno.isPresent()) {
            Turno turnoExistente = optionalTurno.get();

            turnoExistente.setFechaYHora(turnoModificacion.getFechaYHora());

            if (turnoModificacion.getDni() != 0) {
                Paciente pacienteExistente = pacienteService.buscarPacientePorDni(turnoModificacion.getDni());
                if (pacienteExistente != null) {
                    turnoExistente.setPaciente(pacienteExistente);
                } else {
                    LOGGER.error("Paciente con DNI {} no encontrado, no se actualizó.", turnoModificacion.getDni());
                }
            }

            if (turnoModificacion.getMatricula() != null) {
                Odontologo odontologoExistente = odontologoService.buscarOdontologoPorMatricula(turnoModificacion.getMatricula());
                if (odontologoExistente != null) {
                    turnoExistente.setOdontologo(odontologoExistente);
                } else {
                    LOGGER.error("Odontólogo con Matrícula {} no encontrado, no se actualizó.", turnoModificacion.getMatricula());
                }
            }

            turnoRespository.save(turnoExistente);

            TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoExistente, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado correctamente: {}", JsonPrinter.toString(turnoSalidaDto));
            return turnoSalidaDto;
        } else {
            LOGGER.error("Turno no encontrado con ID: {}", idTurno);
            return null;
        }
    }



    @Override
    public void eliminarTurnoPorId(Long id) throws ResourceNotFoundException {
        if (turnoRespository.findById(id).orElse(null) != null) {
            turnoRespository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciete con id: {}", id);
        } else {
            LOGGER.error("No se encontró con id: {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el paciente con el id: " + id);
        }
    }

    private void validarFechaYhoraTurno(LocalDateTime fechaYhoraTurno){
        LocalDateTime fechayHoraActual = LocalDateTime.now();

        if (fechaYhoraTurno.isBefore(fechayHoraActual)) {
            LOGGER.error("Error: La fecha y hora asignada a un turno no puede ser anterior a la fecha y hora actual.");
            throw new IllegalArgumentException("Error: La fecha y hora asignada a un turno no puede ser anterior a la fecha y hora actual.");
        }
    }

    private void configurarMapping(){
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMapping(src -> src.getPaciente().getDni(), TurnoSalidaDto::setDni)
                .addMapping(src -> src.getOdontologo().getMatricula(), TurnoSalidaDto::setMatricula);
    }
}
