package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoActivo implements ValidadorDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DatosReservaConsulta datos){
        // Elección del médico es opcional
        if(datos.idMedico() == null){
            return;
        }

        var medicoEstaActivo = medicoRepository.findActivoById(datos.idMedico());

        if(!medicoEstaActivo){
            throw new ValidacionException("No puede reservar consulta con un médico inactivo");
        }
    }
}
