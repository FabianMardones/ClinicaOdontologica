//Crear nuevo odontologo
const urlRegistrarOdontologo = 'http://localhost:8081/odontologos/registrarOdontologos'
export const nuevoOdontologo = async (odontologo) => {
    try {
        await fetch(urlRegistrarOdontologo, {
            method: 'POST',
            mode: 'cors',
            body: JSON.stringify(odontologo),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        window.location.href = 'index-odontologos.html'
    } catch (error) {
        console.log(error);
    }
}

//Listar odontologos registrados
const urlListarOdontologos = 'http://localhost:8081/odontologos/listarOdontologos'
export const listarOdontologo = async () => {
    try {
        const resultado = await fetch(urlListarOdontologos);
        const odontologos = await resultado.json();
        return odontologos;
    } catch (error) {
        console.log(error);
    }
}


//Eliminar odontologos registrados
const urlEliminarOdontologo = 'http://localhost:8081/odontologos/eliminarOdontologos'
export const eliminarOdontologo = (id) => {
    try {
        fetch(`${urlEliminarOdontologo}/${id}`, {
            method: 'DELETE'
        })
    } catch (error) {
        console.log(error);
    }
}


//Obtener un odontologo por id
const urlObtenerOdontologo = 'http://localhost:8081/odontologos/buscarOdontologoPorId';
export const obtenerOdontologo = async (id) => {
    try {
        const resultado = await fetch(`${urlObtenerOdontologo}/${id}`);
        const odontologo = await resultado.json();
        return odontologo;
    } catch (error) {
        console.log(error);
    }
}


//Actualizar el odontologo
const urlActualizarOdontologo = 'http://localhost:8081/odontologos/actualizarOdontologos';
export const actualizarOdontologo = async (odontologo) => {
    try {
        await fetch(urlActualizarOdontologo, {
            method: 'PUT',
            body: JSON.stringify(odontologo),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        window.location.href = 'index-odontologos.html';
    } catch (error) {
        console.log(error);
    };
};