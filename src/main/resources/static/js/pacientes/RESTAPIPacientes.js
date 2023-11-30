//Crear nuevos pacientes
const urlRegistrar = 'http://localhost:8081/pacientes/registrarPacientes'
export const nuevoPaciente = async (paciente) => {
    try {
        await fetch(urlRegistrar, {
            method: 'POST',
            mode: 'cors',
            body: JSON.stringify(paciente),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        window.location.href = 'index-pacientes.html';
    } catch (error) {
        console.log(error);

    }
}

//Listar pacientes registrados
const urlListar = 'http://localhost:8081/pacientes/listarPacientes'
export const listarPaciente = async () => {
    try {
        const resultado = await fetch(urlListar);
        const pacientes = await resultado.json();
        return pacientes;
    } catch (error) {
        console.log(error);
    }
}

//Eliminar pacientes registrados
const urlEliminar = 'http://localhost:8081/pacientes/eliminarPacientes'
export const eliminarPaciente = async (id) => {
    try {
        await fetch(`${urlEliminar}/${id}`, {
            method: 'DELETE'
        })
    } catch (error) {
        console.log(error);
    }
}

//Obtener un paciente por el id
const urlObtenerPaciente = 'http://localhost:8081/pacientes/buscarPacientePorId'
export const obtenerPaciente = async (id) => {
    try {
        const resultado = await fetch(`${urlObtenerPaciente}/${id}`);
        const paciente = await resultado.json();
        return paciente;
    } catch (error) {
        console.log(error);
    }
}

//Actualizar el paciente
const urlActualizar = 'http://localhost:8081/pacientes/actualizarPacientes'
export const actualizarPaciente = async (paciente) => {
    try {
        await fetch(`${urlActualizar}`, {
            method: 'PUT',
            body: JSON.stringify(paciente),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        window.location.href = 'index-pacientes.html';
    } catch (error) {
        console.log(error);
    };
};