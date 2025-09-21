package brionesrojas.controller;

import brionesrojas.dto.docenteDTO;
import brionesrojas.model.docentes;
import brionesrojas.service.IDocenteService;
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
@RequestMapping("/docentes")
@RequiredArgsConstructor
public class docenteController {
    private final IDocenteService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<docenteDTO>> findAll(){
        List<docenteDTO> list = service.findAll().stream().map(this::convertToDtoWithLinks).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<docenteDTO> findById(@PathVariable("id") Integer id){
        docenteDTO docente = convertToDtoWithLinks(service.findById(id));
        return ResponseEntity.ok(docente);
    }

    @PostMapping
    public ResponseEntity<docenteDTO> save(@Valid @RequestBody docenteDTO dto) throws Exception {
        docentes doc = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(doc.getId_docente()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<docenteDTO> update(@Valid @RequestBody docenteDTO dto, @PathVariable("id") Integer id) {
        docentes doc = service.update(convertToEntity(dto),id);
        docenteDTO newdoc = convertToDtoWithLinks(doc);
        return ResponseEntity.ok(newdoc);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo Nivel 3
    private docenteDTO convertToDtoWithLinks(docentes docente) {
        docenteDTO dto = modelMapper.map(docente, docenteDTO.class);

        // Enlace "self" para el paciente
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(docenteController.class)
                .findById(docente.getId_docente())).withRel("Buscar por Id");

        // Enlace para actualizar el paciente
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(docenteController.class)
                .update(null, docente.getId_docente())).withRel("Actualizar");

        // Enlace para eliminar el paciente
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(docenteController.class)
                .delete(docente.getId_docente())).withRel("Borrar");

        // Enlace para listar todos los pacientes
        Link allPatientsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(docenteController.class)
                .findAll()).withRel("Todos los deocentes");

        // Agregar los enlaces al DTO
        dto.add(selfLink, updateLink, deleteLink, allPatientsLink);

        return dto;
    }

    //Convertir de un dto a un modelo
    private docentes convertToEntity(docenteDTO dto) {
        return modelMapper.map(dto, docentes.class);
    }
}
