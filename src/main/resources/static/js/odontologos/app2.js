import { listarOdontologo, eliminarOdontologo } from './RESTAPIOdontologos.js';
import { listarTurno } from '../turnos/RESAPITurnos.js'

(function(){
    const listado = document.querySelector('#listado-odontologos')

    document.addEventListener('DOMContentLoaded', mostrarOdontologos)

    listado.addEventListener('click', confirmarEliminar)

    async function mostrarOdontologos(){

        const odontologos = await listarOdontologo();

        console.log(odontologos);

        odontologos.forEach(odontologo => {
            const {id, nombre, apellido, matricula} = odontologo

            const row = document.createElement('tr')
            row.innerHTML += `
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                <p class="text-sm leading-5 font-medium text-gray-700 text-lg  font-bold"> ${nombre} </p>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200 ">
                <p class="text-gray-700">${apellido}</p>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                <p class="text-gray-600">${matricula}</p>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200 text-sm leading-5">
                <a href="editar-odontologo.html?id=${id}" class="text-teal-600 hover:text-teal-900 mr-5 font-bold editar">Editar</a>
                <a href="#" data-odontologo="${id}" class="text-red-600 hover:text-red-900 font-bold eliminar">Eliminar</a>
            </td>
        `;

        listado.appendChild(row)
        });
    }

    async function confirmarEliminar(e) {
        if (e.target.classList.contains('eliminar')) {
            const odontologoId = Number(e.target.dataset.odontologo);
    
            // Obtener la matrícula del odontólogo desde la lista de odontólogos
            const odontologos = await listarOdontologo();
            const odontologo = odontologos.find((odo) => odo.id === odontologoId);
    
            const matriculaOdontologo = odontologo.matricula;
    
            const turnos = await listarTurno();
            const odontologoConTurno = turnos.find((turno) => turno.matricula === matriculaOdontologo);
    
            if (odontologoConTurno) {
                Swal.fire({
                    title: "Error al eliminar",
                    text: "El odontólogo tiene turnos asociados. No se puede eliminar.",
                    icon: "error",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Ok",
                });
            } else {
                Swal.fire({
                    title: "Deseas eliminar este odontólogo?",
                    text: "No podrás recuperar el registro!",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Si, Eliminar!",
                }).then((result) => {
                    if (result.isConfirmed) {
                        eliminarOdontologo(odontologoId);
                        e.target.parentElement.parentElement.remove();
                        Swal.fire({
                            title: "Eliminado!",
                            text: "El Odontólogo ha sido eliminado.",
                            icon: "success",
                        });
                    }
                });
            }
        }
    }
    
})()