package brionesrojas.controller;

import brionesrojas.dto.evaluacionDTO;
import brionesrojas.model.evaluaciones;
import brionesrojas.service.IEvaluacionService;
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
@RequestMapping("/evaluaciones")
@RequiredArgsConstructor
public class evaluacionController {
    private final IEvaluacionService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<evaluacionDTO>> findAll(){
        List<evaluacionDTO> list = service.findAll().stream().map(this::convertToDtoWithLinks).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<evaluacionDTO> findById(@PathVariable("id") Integer id){
        evaluacionDTO evaluacion = convertToDtoWithLinks(service.findById(id));
        return ResponseEntity.ok(evaluacion);
    }

    @PostMapping
    public ResponseEntity<evaluacionDTO> save(@Valid @RequestBody evaluacionDTO dto) throws Exception {
        evaluaciones eva = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eva.getId_evaluacion()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<evaluacionDTO> update(@Valid @RequestBody evaluacionDTO dto, @PathVariable("id") Integer id) {
        evaluaciones eva = service.update(convertToEntity(dto),id);
        evaluacionDTO newest = convertToDtoWithLinks(eva);
        return ResponseEntity.ok(newest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo Nivel 3
    private evaluacionDTO convertToDtoWithLinks(evaluaciones evaluacion) {
        evaluacionDTO dto = modelMapper.map(evaluacion, evaluacionDTO.class);

        // Enlace "self" para el paciente
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(evaluacionController.class)
                .findById(evaluacion.getId_evaluacion())).withRel("Buscar por Id");

        // Enlace para actualizar el paciente
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(evaluacionController.class)
                .update(null, evaluacion.getId_evaluacion())).withRel("Actualizar");

        // Enlace para eliminar el paciente
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(evaluacionController.class)
                .delete(evaluacion.getId_evaluacion())).withRel("Borrar");

        // Enlace para listar todos los pacientes
        Link allPatientsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(evaluacionController.class)
                .findAll()).withRel("Todas las evaluaciones");

        // Agregar los enlaces al DTO
        dto.add(selfLink, updateLink, deleteLink, allPatientsLink);

        return dto;
    }

    //Convertir de un dto a un modelo
    private evaluaciones convertToEntity(evaluacionDTO dto) {
        return modelMapper.map(dto, evaluaciones.class);
    }
}
