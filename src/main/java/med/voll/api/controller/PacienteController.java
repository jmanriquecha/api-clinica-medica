package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @GetMapping
    public Page<DatosListaPaciente> listar(@PageableDefault(page = 0, size = 10, sort = {"nombre"})Pageable paginacion){
        return repository.findAll(paginacion).map(DatosListaPaciente::new);
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarPacientes(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                                                 UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = repository.save(new Paciente(datosRegistroPaciente));
        // Return HTTP 201
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoIdentidad(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento()));

        // URL donde encontrar al médico
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DatosActualizacionPaciente datos) {
        Paciente paciente = repository.getReferenceById(datos.id());
        paciente.atualizarInformacion(datos);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoIdentidad(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento()))); // Respuesta HTTP 200
    }

    @DeleteMapping("/{id}")
    @Transactional
    // Elimina datos de manera lógica
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Paciente paciente = repository.getReferenceById(id);
        paciente.inactivar();
        return ResponseEntity.noContent().build(); // Respuesta HTTP 204
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornaDatospaciente(@PathVariable Long id){
        Paciente paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoIdentidad(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento())));
    }
}
