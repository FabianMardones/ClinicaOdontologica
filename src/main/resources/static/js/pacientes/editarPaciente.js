import { obtenerPaciente, actualizarPaciente } from './RESTAPIPacientes.js';
import { mostrarAlerta, validar } from '../funciones.js';
(function(){

    const nombreInput = document.querySelector('#nombre')
    const apellidoInput = document.querySelector('#apellido')
    const dniInput = document.querySelector('#dni')
    const fechaDeIngresoInput = document.querySelector('#date')
    const calleInput = document.querySelector('#calle')
    const numeroInput = document.querySelector('#numero')
    const localidadInput = document.querySelector('#localidad')
    const provinciaInput = document.querySelector('#provincia')
    const idInput = document.querySelector('#id')

    document.addEventListener('DOMContentLoaded', async () => {
        const parametrosUrl = new URLSearchParams(window.location.search);

        const idCliente = Number( parametrosUrl.get('id') );

        const paciente = await obtenerPaciente(idCliente);
        mostrarPaciente(paciente)

        const formulario = document.querySelector('#formulario');
        formulario.addEventListener('submit', validarCliente);
    })

    function mostrarPaciente(paciente){
        console.log(paciente);

        const {id, nombre, apellido, dni, fechaDeIngreso, domicilioSalidaDto: {calle, numero, localidad, provincia}} = paciente;

        nombreInput.value = nombre
        apellidoInput.value = apellido
        dniInput.value = dni
        fechaDeIngresoInput.value = fechaDeIngreso
        calleInput.value = calle
        numeroInput.value = numero
        localidadInput.value = localidad
        provinciaInput.value = provincia
        idInput.value = id;
        
    };

    function validarCliente(e){
        e.preventDefault()

        const paciente = {
            id: Number(idInput.value),
            nombre: nombreInput.value, 
            apellido: apellidoInput.value, 
            dni: Number(dniInput.value), 
            fechaDeIngreso: fechaDeIngresoInput.value,
            domicilioModificacionEntradaDto: {
                calle: calleInput.value, 
                numero: Number(numeroInput.value), 
                localidad: localidadInput.value, 
                provincia: provinciaInput.value
            } 
        };


        if( validar(paciente)) {
            mostrarAlerta("Todos los campos son obligatorios");
            return;
        };

        actualizarPaciente(paciente)


    }
})()