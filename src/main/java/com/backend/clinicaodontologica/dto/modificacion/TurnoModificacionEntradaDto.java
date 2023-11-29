package com.backend.clinicaodontologica.dto.modificacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoModificacionEntradaDto {

    @NotNull(message = "Debe proveerse el id de la cita que desea modificar")
    private Long id;

    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha y la hora de ingreso del paciente")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaYHora;

    @NotNull(message = "El odontologo no puede ser nulo")
    private String nombrePaciente;
    @NotNull(message = "El odontologo no puede ser nulo")
    private String apellidoPaciente;
    @NotBlank(message = "El id no puede estar vacío")
    private int dni;
    @NotNull(message = "El nombre del odontologo no puede ser nulo")
    private String nombreOdontologo;

    @NotNull(message = "El apellido del odontologo no puede ser nulo")
    private String apellidoOdontologo;

    @NotNull(message = "La matricula del odontologo no puede ser nula")
    private String matricula;


    public TurnoModificacionEntradaDto() {
    }


    public TurnoModificacionEntradaDto(Long id, LocalDateTime fechaYHora, String nombrePaciente, String apellidoPaciente, int dni, String nombreOdontologo, String apellidoOdontologo, String matricula) {
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
