package med.voll.api.controller;

import med.voll.api.medico.DatosRegistroMedico;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @PostMapping
    public void registrarMedicos(@RequestBody DatosRegistroMedico datosRegistroMedico){
        System.out.println(datosRegistroMedico);
    }
}
