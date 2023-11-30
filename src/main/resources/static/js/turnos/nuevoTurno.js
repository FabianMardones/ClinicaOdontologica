import { mostrarAlerta, validar } from "../funciones.js"
import { nuevoTurno } from "./RESAPITurnos.js"


(function(){
    const formulario = document.querySelector('#formulario')
    formulario.addEventListener('submit', validarTurno)


    function validarTurno(e){
        e.preventDefault()

        const fechaYHora = document.querySelector('#fechaYHora').value
        const dniPaciente = document.querySelector('#dni-paciente').value
        const nombrePaciente = document.querySelector('#nombre-paciente').value
        const apellidoPaciente = document.querySelector('#apellido-paciente').value
        const matriculaOdontologo = document.querySelector('#matricula-odontologo').value
        const nombreOdontologo = document.querySelector('#nombre-odontologo').value
        const apellidoOdontologo = document.querySelector('#apellido-odontologo').value

        const fechaActual = new Date();

        const dia = formatearFecha(fechaActual.getDate());
        const mes = formatearFecha(fechaActual.getMonth() + 1);
        const anio = fechaActual.getFullYear();

        const hora = formatearFecha(fechaActual.getHours());
        const minutos = formatearFecha(fechaActual.getMinutes());

        const fechaFormateada = `${anio}-${mes}-${dia}T${hora}:${minutos}`;

        console.log(fechaFormateada);

        function formatearFecha(numero) {
            return numero < 10 ? `0${numero}` : numero;
        }


        const turno = {
            fechaYHora,
            nombrePaciente,
            apellidoPaciente,
            dni: dniPaciente,
            nombreOdontologo,
            apellidoOdontologo,
            matricula: matriculaOdontologo
        }

        console.log(turno);

        if (fechaYHora <= fechaFormateada) {
            mostrarAlerta('La fecha y hora no pueden ser anteriores a la fecha actual');
            return;
        }


        if (validar(turno)) {
            mostrarAlerta('Todos los campos son obligatorios')
            return;
        }

        nuevoTurno(turno)
        
    }
})()