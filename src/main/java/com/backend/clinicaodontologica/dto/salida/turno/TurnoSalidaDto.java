package com.backend.clinicaodontologica.dto.salida.turno;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoSalidaDto {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaYHora;
    private String nombrePaciente;
    private String apellidoPaciente;
    private int dni;
    private String nombreOdontologo;
    private String apellidoOdontologo;
    private String matricula;


    public TurnoSalidaDto() {
    }

    public TurnoSalidaDto(Long id, LocalDateTime fechaYHora, String nombrePaciente, String apellidoPaciente, int dni, String nombreOdontologo, String apellidoOdontologo, String matricula) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.dni = dni;
        this.nombreOdontologo = nombreOdontologo;
        this.apellidoOdontologo = apellidoOdontologo;
        this.matricula = matricula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombreOdontologo() {
        return nombreOdontologo;
    }

    public void setNombreOdontologo(String nombreOdontologo) {
        this.nombreOdontologo = nombreOdontologo;
    }

    public String getApellidoOdontologo() {
        return apellidoOdontologo;
    }

    public void setApellidoOdontologo(String apellidoOdontologo) {
        this.apellidoOdontologo = apellidoOdontologo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
