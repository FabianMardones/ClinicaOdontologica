import { listarPaciente, eliminarPaciente } from './RESTAPIPacientes.js'
import { listarTurno } from '../turnos/RESAPITurnos.js'

(function(){
    const listado = document.querySelector('#listado-pacientes');

    document.addEventListener('DOMContentLoaded', mostrarPacientes);

    listado.addEventListener('click', confirmarEliminar);

    async function mostrarPacientes(){

        const pacientes = await listarPaciente();

        console.log(pacientes);

        pacientes.forEach(paciente => {
            const {id, nombre, apellido, dni, fechaDeIngreso, domicilioSalidaDto: {calle, numero, localidad, provincia}} = paciente;

            const row = document.createElement('tr');
            row.innerHTML += `
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                    <p class="text-sm leading-5 font-medium text-gray-700 text-lg  font-bold"> ${nombre} </p>
                </td>
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200 ">
                    <p class="text-gray-700">${apellido}</p>
                </td>
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                    <p class="text-gray-600">${dni}</p>
                </td>
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                    <p class="text-gray-600">${fechaDeIngreso}</p>
                </td>
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                    <p class="text-gray-600">${calle}</p>
                </td>
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                    <p class="text-gray-600">${numero}</p>
                </td>
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                    <p class="text-gray-600">${localidad}</p>
                </td>
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                    <p class="text-gray-600">${provincia}</p>
                </td>
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200 text-sm leading-5">
                    <a href="editar-paciente.html?id=${id}" class="text-teal-600 hover:text-teal-900 mr-5 font-bold editar">Editar</a>
                    <a href="#" data-paciente="${id}" class="text-red-600 hover:text-red-900 font-bold eliminar">Eliminar</a>
                </td>
            `;

            listado.appendChild(row);
        });
    }

    async function confirmarEliminar(e) {
        if (e.target.classList.contains('eliminar')) {
            const pacienteId = Number(e.target.dataset.paciente);

            const pacientes = await listarPaciente();
            const paciente = pacientes.find((pac) => pac.id === pacienteId);
            const dniPaciente = paciente.dni;
    
            const turnos = await listarTurno();
            const pacienteConTurno = turnos.find((turno) => turno.dni === dniPaciente);
    
            if (pacienteConTurno) {
                Swal.fire({
                    title: "Error al eliminar",
                    text: "El paciente tiene turnos asociados. No se puede eliminar.",
                    icon: "error",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Ok",
                });
            } else {
                Swal.fire({
                    title: "Deseas eliminar este paciente?",
                    text: "No podrás recuperar el registro!",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Si, Eliminar!",
                }).then((result) => {
                    if (result.isConfirmed) {
                        eliminarPaciente(pacienteId);
                        e.target.parentElement.parentElement.remove();
                        Swal.fire({
                            title: "Eliminado!",
                            text: "El Paciente ha sido eliminado.",
                            icon: "success",
                        });
                    }
                });
            }
        }
    }
})()