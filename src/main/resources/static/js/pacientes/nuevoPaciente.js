import { mostrarAlerta, validar } from '../funciones.js';
import { nuevoPaciente, listarPaciente } from './RESTAPIPacientes.js';

(function(){
    const formulario = document.querySelector('#formulario');
    formulario.addEventListener('submit', validarPaciente);

    async function validarPaciente(e){
        e.preventDefault();

        const fechaActual = new Date();

        const dia = formatearFecha(fechaActual.getDate());
        const mes = formatearFecha(fechaActual.getMonth() + 1);
        const anio = fechaActual.getFullYear();

        const fechaFormateada = `${anio}-${mes}-${dia}`;

        console.log(fechaFormateada);

        function formatearFecha(numero) {
            return numero < 10 ? `0${numero}` : numero;
        }

        const nombre = document.querySelector('#nombre').value;
        const apellido = document.querySelector('#apellido').value;
        const dni = Number(document.querySelector('#dni').value);
        const fechaDeIngreso = document.querySelector('#date').value;
        const calle = document.querySelector('#calle').value;
        const numero = Number(document.querySelector('#numero').value);
        const localidad = document.querySelector('#localidad').value;
        const provincia = document.querySelector('#provincia').value;

        const paciente = {
            nombre,
            apellido,
            dni,
            fechaDeIngreso,
            domicilioEntradaDto: {
                calle,
                numero,
                localidad,
                provincia}
            };

        const pacientes = await listarPaciente();
        const pacienteConDni = pacientes.find((pac) => pac.dni === dni);

        if (pacienteConDni) {
            mostrarAlerta("El Dni del paciente ya se está registrado");
            return;
        }

        if (fechaDeIngreso < fechaFormateada || fechaDeIngreso > fechaFormateada) {
            mostrarAlerta('La fecha de ingreso no puede ser anterior ni posterior a hoy')
            return;
        }

        
        if( validar(paciente)) {
            mostrarAlerta("Todos los campos son obligatorios");
            return;
        };

        nuevoPaciente(paciente)

    };

})()