package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.DniDuplicadoException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.PacienteRepository;
import com.backend.clinicaodontologica.repository.TurnoRespository;
import com.backend.clinicaodontologica.service.IPacienteService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {
    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private PacienteRepository pacienteRepository;

    private TurnoRespository turnoRespository;
    private ModelMapper modelMapper;

    public PacienteService(PacienteRepository pacienteRepository, TurnoRespository turnoRespository,ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.turnoRespository = turnoRespository;
        this.modelMapper = modelMapper;
        configurarMapping();
    }

    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) {

        try{
            //convertimos mediante el mapper dtoEntrada a entidad
            LOGGER.info("PacienteEntradaDto: {}", JsonPrinter.toString(paciente));
            Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);

            //mandamos a persistir a la capa dao y obtenemos una entidad
            Paciente pacienteAPersistir = pacienteRepository.save(pacienteEntidad);
            //Transformamos la entidad obtenida en salidaDto
            PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteAPersistir, PacienteSalidaDto.class);
            LOGGER.info("PacienteSalidaDto: " + JsonPrinter.toString(pacienteSalidaDto));
            return pacienteSalidaDto;
        }catch (DataIntegrityViolationException e){
            // Manejar la excepción de violación de integridad de datos (por ejemplo, DNI duplicado)
            LOGGER.error("Error al registrar paciente: {}", e.getMessage());
            // Puedes lanzar una excepción personalizada o devolver un objeto indicando el error, según tus necesidades
            throw new DniDuplicadoException("Error al registrar paciente: DNI duplicado");
        }
    }


    public List<PacienteSalidaDto> listarPacientes() {
        List<PacienteSalidaDto> pacienteSalidaDto = pacienteRepository.findAll().stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class)).toList();
        LOGGER.info("Listado de todos los pacientes: {}", pacienteSalidaDto);
        return pacienteSalidaDto;
    }

    @Override
    public PacienteSalidaDto buscarPacientePorId(Long id) {
        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        PacienteSalidaDto pacienteEncontrado = null;
        if (pacienteBuscado != null) {
            pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
            LOGGER.info("Paciente Encontrado: {}", pacienteEncontrado);
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
        }
        return pacienteEncontrado;
    }

    @Override
    public PacienteSalidaDto actualizarPaciente(PacienteModificacionEntradaDto paciente) {
        Paciente pacienteRecibido = modelMapper.map(paciente, Paciente.class);
        Paciente pacienteActualizar = pacienteRepository.findById(pacienteRecibido.getId()).orElse(null);

        PacienteSalidaDto pacienteSalidaDto = null;

        if (pacienteActualizar != null) {
            pacienteActualizar = pacienteRecibido;
            pacienteRepository.save(pacienteActualizar);

            pacienteSalidaDto = modelMapper.map(pacienteActualizar, PacienteSalidaDto.class);
            LOGGER.warn("Paciente actualizado correctamente: {}", JsonPrinter.toString(pacienteSalidaDto));

        } else {
            LOGGER.error("Paciente no encontrado, por lo que no se acutalizó ningún registro");
            //lanzar excepcion correspondiente
        }


        return pacienteSalidaDto;
    }

    @Override
    public void eliminarPacientePorId(Long id) throws ResourceNotFoundException {
        Paciente paciente = pacienteRepository.findById(id).orElse(null);
        if (paciente != null) {
            List<Turno> turnosAsociados = turnoRespository.findByPacienteId(id);
            if (!turnosAsociados.isEmpty()) {
                LOGGER.error("No se puede eliminar el paciente con id {} ya que está asociado a turnos");
                throw new ResourceNotFoundException("No se puede eliminar el paciente ya que está asociado a turnos");
            }
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id: {}", id);
        } else {
            LOGGER.error("No se encontró el paciente con id{}: ", id);
            throw new ResourceNotFoundException("No se ha encontrado el paciente con el id: " + id);
        }
    }

    @Override
    public Paciente buscarPacientePorDni(int dni) {
        return modelMapper.map(pacienteRepository.findByDni(dni), Paciente.class);
    }

    private void configurarMapping() {
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(modelMapper -> modelMapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));
        modelMapper.typeMap(PacienteModificacionEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteModificacionEntradaDto::getDomicilioModificacionEntradaDto, Paciente::setDomicilio));
    }
}