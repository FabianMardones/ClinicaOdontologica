//Crear un nuevo turno
const urlRegistrarTurno = 'http://localhost:8081/turnos/registrarTurnos'
export const nuevoTurno = async (turno) => {
    try {
        await fetch(urlRegistrarTurno, {
            method: 'POST',
            body: JSON.stringify(turno),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        window.location.href = 'index-turnos.html'
    } catch (error) {
        console.log(error);
    }
}

//Listar turnos registrados
const urlListarTurnos = 'http://localhost:8081/turnos/listarTurnos'
export const listarTurno = async() => {
    try {
        const resultado = await fetch(urlListarTurnos);
        const turnos = await resultado.json();
        return turnos;
    } catch (error) {
        console.log(error);
    }
}


//Eliminar un turno
const utrlEliminarTurno = 'http://localhost:8081/turnos/eliminarTurnos'
export const eliminarTurno = (id) => {
    try {
        fetch(`${utrlEliminarTurno}/${id}`, {
            method: 'DELETE'
        })
    } catch (error) {
        console.log(error);
    }
}

//Obtener un turno por id
const urlObtenerTurno = 'http://localhost:8081/turnos/buscarTurnoPorId';
export const obtenerTurno = async (id) => {
    try {
        const resultado = await fetch(`${urlObtenerTurno}/${id}`);
        const turno = await resultado.json();
        return turno;
    } catch (error) {
        console.log(error);
    }
}

//Actualizar el Turno
const urlActualizarTurno = 'http://localhost:8081/turnos/actualizarTurnos';
export const actualizarTurno = async (turno) => {
    try {
        await fetch(urlActualizarTurno, {
            method: 'PUT',
            body: JSON.stringify(turno),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        window.location.href = 'index-turnos.html';
    } catch (error) {
        console.log(error);
    };
};
