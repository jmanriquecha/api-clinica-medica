package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroPaciente(
        String nombre,
        String email,
        String documento,
        DatosDireccion direccion
) {
}
