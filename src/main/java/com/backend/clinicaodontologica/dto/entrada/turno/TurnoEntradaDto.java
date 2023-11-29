package com.backend.clinicaodontologica.dto.entrada.turno;

import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class TurnoEntradaDto {

    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha y la hora de ingreso del paciente")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaYHora;

    @NotNull(message = "El Nombre del Paciente asociado al turno no puede ser nulo")
    private String nombrePaciente;

    @NotNull(message = "El apellido del paciente asociado al turno no puede ser nulo")
    private String apellidoPaciente;

    @NotNull(message = "El DNI del paciente asociado al turno no puede ser nulo")
    @Size(max = 12, message = "El campo apellido debe etener hasta 50 caracteres")
    private int dni;

    @NotNull(message = "El nombre del odontologo asociado al turno no puede ser nulo")
    private String nombreOdontologo;

    @NotNull(message = "El apellido del odontologo asociado al turno no puede ser nulo")
    private String apellidoOdontologo;

    @NotNull(message = "La matricula del odontologo asociada al turno no puede ser nulo")
    private String matricula;

    public TurnoEntradaDto() {
    }

    public TurnoEntradaDto(LocalDateTime fechaYHora, String nombrePaciente, String apellidoPaciente, int dni, String nombreOdontologo, String apellidoOdontologo, String matricula) {
        this.fechaYHora = fechaYHora;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.dni = dni;
        this.nombreOdontologo = nombreOdontologo;
        this.apellidoOdontologo = apellidoOdontologo;
        this.matricula = matricula;
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

