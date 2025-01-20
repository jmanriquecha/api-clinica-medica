package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

public class ValidarMedicoActivo {

    private MedicoRepository medicoRepository;

    public void validar(DatosReservaConsulta datos){
        // Elección del médico es opcional
        if(datos.idMedico() == null){
            return;
        }

        var medicoEstaActivo = medicoRepository.findActiveById(datos.idPaciente());

        if(!medicoEstaActivo){
            throw new ValidacionException("No puede reservar consulta con un médico inactivo");
        }
    }
}
