package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

public class ValidacionPacienteActivo {

    private PacienteRepository pacienteRepository;

    public void validar(DatosReservaConsulta datos){
        var pacienteEstaActivo = pacienteRepository.findActiveById(datos.idPaciente());

        if(!pacienteEstaActivo){
            throw new ValidacionException("No puede reservar consulta con un paciente inactivo");
        }
    }
}
