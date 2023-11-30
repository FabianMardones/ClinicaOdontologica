import { listarPaciente } from '../pacientes/RESTAPIPacientes.js';
import { listarOdontologo } from '../odontologos/RESTAPIOdontologos.js';

(function () {
    const selectDniPaciente = document.getElementById('dni-paciente');
    const nombrePacienteInput = document.getElementById('nombre-paciente');
    const apellidoPacienteInput = document.getElementById('apellido-paciente');

    const selectMatriculaOdontologo = document.getElementById('matricula-odontologo');
    const nombreOdontologoInput = document.getElementById('nombre-odontologo')
    const apellidoOdontologoInput = document.getElementById('apellido-odontologo')
  
    document.addEventListener('DOMContentLoaded', listarPacientesYOdontologosDisponibles);
  
    async function listarPacientesYOdontologosDisponibles() {
      const pacientes = await listarPaciente();
      const odontologos = await listarOdontologo();
  
      console.log(pacientes);
      console.log(odontologos);
  
      pacientes.forEach(paciente => {
        const { nombre, apellido, dni } = paciente;

        const option = document.createElement('option');
        option.value = dni;
        option.text = dni;
        option.dataset.nombre = nombre;
        option.dataset.apellido = apellido;
  
        selectDniPaciente.appendChild(option);
      });
  
      odontologos.forEach(odontologo => {
        const { nombre, apellido, matricula } = odontologo;
        
        const option = document.createElement('option');
        option.value = matricula;
        option.text = matricula;
        option.dataset.nombre = nombre;
        option.dataset.apellido = apellido;

        selectMatriculaOdontologo.appendChild(option)
      });
  

      selectDniPaciente.addEventListener('change', actualizarDatosPaciente);
      selectMatriculaOdontologo.addEventListener('change', actualizarDatosOdontologo)
    }
  
    function actualizarDatosPaciente() {
      const selectedOption = selectDniPaciente.options[selectDniPaciente.selectedIndex];
  
      const nombrePaciente = selectedOption.dataset.nombre;
      const apellidoPaciente = selectedOption.dataset.apellido;
  
      nombrePacienteInput.value = nombrePaciente;
      apellidoPacienteInput.value = apellidoPaciente;

      nombrePacienteInput.disabled = true;
      apellidoPacienteInput.disabled = true;
    }

    function actualizarDatosOdontologo(){
        const selectedOption = selectMatriculaOdontologo.options[selectMatriculaOdontologo.selectedIndex];

        const nombreOdontologo = selectedOption.dataset.nombre;
        const apellidoOdontologo = selectedOption.dataset.apellido;


        nombreOdontologoInput.value = nombreOdontologo;
        apellidoOdontologoInput.value = apellidoOdontologo;

    }
  })();
  