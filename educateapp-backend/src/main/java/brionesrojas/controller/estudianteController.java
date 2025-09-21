package brionesrojas.controller;

import brionesrojas.dto.estudianteDTO;
import brionesrojas.model.estudiantes;
import brionesrojas.service.IEstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class estudianteController {
    private final IEstudianteService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<estudianteDTO>> findAll(){
        List<estudianteDTO> list = service.findAll().stream().map(this::convertToDtoWithLinks).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<estudianteDTO> findById(@PathVariable("id") Integer id){
        estudianteDTO estudiante = convertToDtoWithLinks(service.findById(id));
        return ResponseEntity.ok(estudiante);
    }

    @PostMapping
    public ResponseEntity<estudianteDTO> save(@Valid @RequestBody estudianteDTO dto) throws Exception {
        estudiantes est = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(est.getId_estudiante()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<estudianteDTO> update(@Valid @RequestBody estudianteDTO dto, @PathVariable("id") Integer id) {
        estudiantes est = service.update(convertToEntity(dto),id);
        estudianteDTO newest = convertToDtoWithLinks(est);
        return ResponseEntity.ok(newest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo Nivel 3
    private estudianteDTO convertToDtoWithLinks(estudiantes estudiante) {
        estudianteDTO dto = modelMapper.map(estudiante, estudianteDTO.class);

        // Enlace "self" para el paciente
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(estudianteController.class)
                .findById(estudiante.getId_estudiante())).withRel("Buscar por Id");

        // Enlace para actualizar el paciente
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(estudianteController.class)
                .update(null, estudiante.getId_estudiante())).withRel("Actualizar");

        // Enlace para eliminar el paciente
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(estudianteController.class)
                .delete(estudiante.getId_estudiante())).withRel("Borrar");

        // Enlace para listar todos los pacientes
        Link allPatientsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(estudianteController.class)
                .findAll()).withRel("Todos los estudiantes");

        // Agregar los enlaces al DTO
        dto.add(selfLink, updateLink, deleteLink, allPatientsLink);

        return dto;
    }

    //Convertir de un dto a un modelo
    private estudiantes convertToEntity(estudianteDTO dto) {
        return modelMapper.map(dto, estudiantes.class);
    }
}
