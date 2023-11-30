import { eliminarTurno, listarTurno } from "./RESAPITurnos.js"

(function(){
    const listado = document.querySelector('#listado-turnos')

    document.addEventListener('DOMContentLoaded', mostrarTurnos)

    listado.addEventListener('click', confirmarEliminar)

    async function mostrarTurnos(){

        const turnos = await listarTurno()

        console.log(turnos);

        turnos.forEach(turno => {
            const {id, nombrePaciente, apellidoPaciente, dni, fechaYHora, nombreOdontologo, apellidoOdontologo, matricula} = turno

            const row = document.createElement('tr')
            row.innerHTML += `
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200">
                <p class="text-sm leading-5 font-medium text-gray-700 text-lg  font-bold"> ${fechaYHora} </p>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200 ">
                <p class="text-gray-700">${nombrePaciente}</p>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                <p class="text-gray-600">${apellidoPaciente}</p>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                <p class="text-gray-600">${dni}</p>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
                <p class="text-gray-600">${nombreOdontologo}</p>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
            <p class="text-gray-600">${apellidoOdontologo}</p>
            </td>
                <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200  leading-5 text-gray-700">    
            <p class="text-gray-600">${matricula}</p>
            </td>
            <td class="px-6 py-4 whitespace-no-wrap border-b border-gray-200 text-sm leading-5">
                <a href="editar-turno.html?id=${id}" class="text-teal-600 hover:text-teal-900 mr-5 font-bold editar">Editar</a>
                <a href="#" data-turno="${id}" class="text-red-600 hover:text-red-900 font-bold eliminar">Eliminar</a>
            </td>
        `;

        listado.appendChild(row)
        });
    }

    function confirmarEliminar(e){
        if (e.target.classList.contains('eliminar')) {
            
            const turnoId = Number(e.target.dataset.turno);

            Swal.fire({
                title: "Deseas eliminar este odontologo?",
                text: "No podrás recuperar el registro!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Si, Eliminar!"
              }).then((result) => {
                if (result.isConfirmed) {
                    eliminarTurno(turnoId)
                    e.target.parentElement.parentElement.remove()
                  Swal.fire({
                    title: "Eliminado!",
                    text: "El Odontologo ha sido eliminado.",
                    icon: "success"
                  });
                }
              });
        }
    }
})()