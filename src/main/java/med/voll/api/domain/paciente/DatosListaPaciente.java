package med.voll.api.domain.paciente;

public record DatosListaPaciente (
        String nombre,
        String email,
        String documentoId
) {
    public DatosListaPaciente(Paciente paciente){
        this(paciente.getNombre(), paciente.getEmail(), paciente.getDocumentoIdentidad());
    }
}
