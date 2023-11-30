import { mostrarAlerta, validar } from '../funciones.js';
import { nuevoOdontologo, listarOdontologo } from './RESTAPIOdontologos.js'

(function(){
    const formulario = document.querySelector('#formulario')
    formulario.addEventListener('submit', validarOdontologo)

    async function validarOdontologo(e){
        e.preventDefault()

        const nombre = document.querySelector('#nombre').value;
        const apellido = document.querySelector('#apellido').value;
        const matricula = document.querySelector('#matricula').value;

        const odontologo = {nombre, apellido, matricula}

        const odontologos = await listarOdontologo();
        const odontologosConMatricula = odontologos.find((odo) => odo.matricula === matricula)

        if (odontologosConMatricula) {
            mostrarAlerta("La matrícula ya se encuentra registrada")
            return;
        }

        if(validar(odontologo)){
            mostrarAlerta('Error! Todos los campos son obligatorios')
            return;
        }

        nuevoOdontologo(odontologo)
    }
})()