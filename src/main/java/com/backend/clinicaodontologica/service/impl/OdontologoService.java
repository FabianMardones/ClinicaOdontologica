package com.backend.clinicaodontologica.service.impl;


import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.DniDuplicadoException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.OdontologoRepository;
import com.backend.clinicaodontologica.repository.TurnoRespository;
import com.backend.clinicaodontologica.service.IOdontologoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;

    private TurnoRespository turnoRespository;
    private ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, TurnoRespository turnoRespository,ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.turnoRespository = turnoRespository;
        this.modelMapper = modelMapper;

    }

    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo) {
        try{
            LOGGER.info("Odontologo encontrado: {}", JsonPrinter.toString(odontologo));

            Odontologo odontologoEntidad = modelMapper.map(odontologo, Odontologo.class);
            Odontologo odontolosoAPersitir = odontologoRepository.save(odontologoEntidad);

            OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontolosoAPersitir, OdontologoSalidaDto.class);

            LOGGER.info("OdontologoSalidaDto: {}", JsonPrinter.toString(odontologoSalidaDto));
            return odontologoSalidaDto;
        }catch (DataIntegrityViolationException e){
            LOGGER.error("Error al registrar odontologo: {}", e.getMessage());

            throw new DniDuplicadoException("Error al registrar Odontoglo: MATRICULA duplicada");
        }

    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoSalidaDto odontolodoEncontrado = null;

        if (odontologoBuscado != null){
            odontolodoEncontrado = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo Encontrado: {}", odontolodoEncontrado);
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
        }
        return odontolodoEncontrado;
    }

    @Override
    public List<OdontologoSalidaDto> listarOdontologos() {
        List<OdontologoSalidaDto> odontologoSalidaDto = odontologoRepository.findAll().stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class)).toList();
        LOGGER.info("Listado de todos los odontologos: {}", odontologoSalidaDto);
        return odontologoSalidaDto;
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        Odontologo odontologo = odontologoRepository.findById(id).orElse(null);
        if (odontologo != null) {
            List<Turno> turnoAsociado =  turnoRespository.findByOdontologoId(id);
            if (!turnoAsociado.isEmpty()) {
                LOGGER.error("No se puede eliminar el odontologo con id {} ya que está asociado a turnos", id);
                throw new ResourceNotFoundException("No se puede eliminar el odontólogo ya que está asociado a turnos");
            }
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id: {}", id);
        } else {
            LOGGER.error("No se encontró el odontólogo con id: {}", id);
            throw new ResourceNotFoundException("No se encontró el odontologo con el id: " + id);
        }
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoModificacionEntradaDto odontolodo) {
        Odontologo odontologoRecibido = modelMapper.map(odontolodo, Odontologo.class);
        Odontologo odontologoActualizar = odontologoRepository.findById(odontologoRecibido.getId()).orElse(null);

        OdontologoSalidaDto odontologoSalidaDto = null;

        if (odontologoActualizar != null) {
            odontologoActualizar = odontologoRecibido;
            odontologoRepository.save(odontologoActualizar);

            odontologoSalidaDto = modelMapper.map(odontologoActualizar, OdontologoSalidaDto.class);
            LOGGER.warn("Odontologo actualizado correctamente: {}", JsonPrinter.toString(odontologoSalidaDto));
        } else {
            LOGGER.error("Odontologo no encontrado, por lo que no se actualizó ningún registro");
        }
        return odontologoSalidaDto;
    }

    @Override
    public Odontologo buscarOdontologoPorMatricula(String matricula) {
        Odontologo odontologoBuscado = odontologoRepository.findByMatricula(matricula);
        Odontologo odontologoEncontrado = null;

        if (odontologoBuscado != null){
            odontologoEncontrado = modelMapper.map(odontologoBuscado, Odontologo.class);
            LOGGER.info("Odontologo encontado: {}", odontologoEncontrado);
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
        }
        return odontologoEncontrado;
    }
}