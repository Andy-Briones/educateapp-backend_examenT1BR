package brionesrojas.controller;

import brionesrojas.dto.horarioDTO;
import brionesrojas.model.horarios;
import brionesrojas.service.IHorarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/horarios")
@RequiredArgsConstructor
public class horarioController {
    private final IHorarioService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<horarioDTO>> findAll(){
        List<horarioDTO> list = service.findAll().stream().map(this::convertToDtoWithLinks).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<horarioDTO> findById(@PathVariable("id") Integer id){
        horarioDTO horario = convertToDtoWithLinks(service.findById(id));
        return ResponseEntity.ok(horario);
    }

    @PostMapping
    public ResponseEntity<horarioDTO> save(@Valid @RequestBody horarioDTO dto) throws Exception {
        horarios hor = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(hor.getId_horario()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<horarioDTO> update(@Valid @RequestBody horarioDTO dto, @PathVariable("id") Integer id) {
        horarios hor = service.update(convertToEntity(dto),id);
        horarioDTO newhor = convertToDtoWithLinks(hor);
        return ResponseEntity.ok(newhor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo Nivel 3
    private horarioDTO convertToDtoWithLinks(horarios horario) {
        horarioDTO dto = modelMapper.map(horario, horarioDTO.class);

        // Enlace "self" para el paciente
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(horarioController.class)
                .findById(horario.getId_horario())).withRel("Buscar por Id");

        // Enlace para actualizar el paciente
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(horarioController.class)
                .update(null, horario.getId_horario())).withRel("Actualizar");

        // Enlace para eliminar el paciente
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(horarioController.class)
                .delete(horario.getId_horario())).withRel("Borrar");

        // Enlace para listar todos los pacientes
        Link allPatientsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(horarioController.class)
                .findAll()).withRel("Todos los horarios");

        // Agregar los enlaces al DTO
        dto.add(selfLink, updateLink, deleteLink, allPatientsLink);

        return dto;
    }

    //Convertir de un dto a un modelo
    private horarios convertToEntity(horarioDTO dto) {
        return modelMapper.map(dto, horarios.class);
    }
}
