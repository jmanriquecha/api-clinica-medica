package med.voll.api.paciente;

import med.voll.api.direccion.DatosDireccion;

public record DatosRegistroPaciente(
        String nombre,
        String email,
        String documento,
        DatosDireccion direccion
) {
}
