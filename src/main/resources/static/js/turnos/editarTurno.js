import { obtenerTurno, actualizarTurno } from "./RESAPITurnos.js";
import { mostrarAlerta, validar } from '../funciones.js';


(function(){
    const fechaYHoraInput = document.querySelector('#fechaYHora');
    const dniPacienteInput = document.querySelector('#dni-paciente');
    const nombrePacienteInput = document.querySelector('#nombre-paciente');
    const apellidoPacienteInput = document.querySelector('#apellido-paciente');
    const matriculaOdontologoInput = document.querySelector('#matricula-odontologo');
    const nombreOdontologoInput = document.querySelector('#nombre-odontologo');
    const apellidoOdontologoInput = document.querySelector('#apellido-odontologo');
    const idInput = document.querySelector('#id');

    document.addEventListener('DOMContentLoaded', async () => {
        const parametrosUrl = new URLSearchParams(window.location.search);

        const idTurno = Number(parametrosUrl.get('id'));

        const turno = await obtenerTurno(idTurno);
        mostrarTurno(turno)

        const formulario = document.querySelector('#formulario')
        formulario.addEventListener('submit', validarTurno)
    })

    function mostrarTurno(turno){
        console.log(turno);

        const {fechaYHora, id, nombrePaciente, apellidoPaciente, dni, nombreOdontologo, apellidoOdontologo, matricula} = turno

        fechaYHoraInput.value = fechaYHora;
        dniPacienteInput.value = dni;
        dniPacienteInput.textContent = dni;
        nombrePacienteInput.value = nombrePaciente;
        apellidoPacienteInput.value = apellidoPaciente;
        matriculaOdontologoInput.value = matricula;
        matriculaOdontologoInput.textContent = matricula;
        nombreOdontologoInput.value = nombreOdontologo;
        apellidoOdontologoInput.value = apellidoOdontologo;
        idInput.value = id

    }

    function validarTurno(e){
        e.preventDefault()

        const turno = {
            id: Number(idInput.value),
            fechaYHora: fechaYHoraInput.value,
            nombrePaciente: nombrePacienteInput.value,
            apellidoPaciente: apellidoPacienteInput.value,
            dni: dniPacienteInput.value,
            nombreOdontologo: nombreOdontologoInput.value,
            apellidoOdontologo: apellidoOdontologoInput.value,
            matricula: matriculaOdontologoInput.value
        }

        if (validar(turno)) {
            mostrarAlerta('Todos los campos son obligatorios');
            return;
        };

        actualizarTurno(turno);
    }
})()