import { obtenerOdontologo, actualizarOdontologo } from './RESTAPIOdontologos.js'
import { mostrarAlerta, validar } from '../funciones.js';

(function(){

        const nombreInput = document.querySelector('#nombre');
        const apellidoInput = document.querySelector('#apellido');
        const matriculaInput = document.querySelector('#matricula');
        const idInput = document.querySelector('#id');


        document.addEventListener('DOMContentLoaded', async () => {
            const parametrosUrl = new URLSearchParams(window.location.search);

            const idOdontologo = Number(parametrosUrl.get('id'))

            const odontologo = await obtenerOdontologo(idOdontologo);
            mostrarOdontologo(odontologo)

            const formulario = document.querySelector('#formulario')
            formulario.addEventListener('submit', validarOdontologo)
        });

        function mostrarOdontologo(odontologo){
            console.log(odontologo);

            const {id, nombre, apellido, matricula} = odontologo;

            nombreInput.value = nombre;
            apellidoInput.value = apellido;
            matriculaInput.value = matricula;
            idInput.value = id;
        }

        function validarOdontologo(e){
            e.preventDefault();

            const odontologo = {
                id: Number(idInput.value),
                nombre : nombreInput.value,
                apellido: apellidoInput.value,
                matricula: matriculaInput.value
            }

            if ( validar(odontologo) ) {
                mostrarAlerta('Todos los campos son obligatorios');
                return;
            };
    
            actualizarOdontologo(odontologo);

        };

})()