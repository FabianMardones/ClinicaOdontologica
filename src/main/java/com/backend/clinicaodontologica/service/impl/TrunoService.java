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

import java.util.List;

@Service
public class TrunoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(ITurnoService.class);
    private final TurnoRespository turnoRespository;
    private final ModelMapper modelMapper;
    private final OdontologoService odontologoService;
    private final PacienteService pacienteService;

    public TrunoService(TurnoRespository turnoRespository, ModelMapper modelMapper, OdontologoService odontologoService, PacienteService pacienteService) {
        this.turnoRespository = turnoRespository;
        this.modelMapper = modelMapper;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        configurarMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoDto) {
        //convertimos mediante el mapper dtoEntrada a entidad
        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turnoDto));
        Turno turnoEntidad = modelMapper.map(turnoDto, Turno.class);

        //mandamos a persistir a la capa dao y obtenemos una entidad
        Turno turnoAPersistir = turnoRespository.save(turnoEntidad);
        //Transformamos la entidad obtenida en salidaDto
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
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
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) {
        Turno turnoRecibido = modelMapper.map(turno, Turno.class);
        Turno turnoActualizar = turnoRespository.findById(turnoRecibido.getId()).orElse(null);

        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoActualizar != null) {
            turnoActualizar = turnoRecibido;
            turnoRespository.save(turnoActualizar);

            turnoSalidaDto = modelMapper.map(turnoActualizar, TurnoSalidaDto.class);
            LOGGER.warn("Paciente actualizado correctamente: {}", JsonPrinter.toString(turnoSalidaDto));
        } else {
            LOGGER.error("Turno no encontrado, por lo que no se actualizó ningún registro");
        }
        return turnoSalidaDto;
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

    private void configurarMapping(){
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getPacienteEntradaDto, Turno::setPaciente))
                .addMappings(modelMapper -> modelMapper.map(TurnoEntradaDto::getOdontologoEntradaDto, Turno::setOdontologo));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto))
                .addMappings(modelMapper -> modelMapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto));
        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoModificacionEntradaDto::getPacienteModificacionEntradaDto, Turno::setPaciente))
                .addMappings(mapper -> mapper.map(TurnoModificacionEntradaDto::getOdontologoModificacionEntradaDto, Turno::setOdontologo));
    }
}
