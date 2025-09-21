package brionesrojas.controller;

import brionesrojas.dto.cursoDTO;
import brionesrojas.dto.estudianteDTO;
import brionesrojas.model.cursos;
import brionesrojas.model.docentes;
import brionesrojas.repository.IDocenteRepository;
import brionesrojas.service.ICursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class cursoController {
    private final ICursoService service;
    private final ModelMapper modelMapper;
    private final IDocenteRepository repo;

    @GetMapping
    public ResponseEntity<List<cursoDTO>> findAll(){
        List<cursoDTO> list = service.findAll().stream().map(this::convertToDtoWithLinks).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<cursoDTO> findById(@PathVariable("id") Integer id){
        cursoDTO curso = convertToDtoWithLinks(service.findById(id));
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    public ResponseEntity<cursoDTO> save(@Valid @RequestBody cursoDTO dto) throws Exception {
//        cursos cur = service.save(convertToEntity(dto));
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cur.getId_curso()).toUri();
//        return ResponseEntity.created(location).build();
//        cursos curso = convertToEntity(dto);
//
//        // Buscar el docente
//        docentes docente = repo.findById(dto.getDocente())
//                .orElseThrow(() -> new RuntimeException("Docente no encontrado con id " + dto.getDocente()));
//
//        curso.setDocente(docente);
//
//        cursos cur = service.save(curso);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(cur.getId_curso())
//                .toUri();
        //DTO a Entidad
        cursos curso = modelMapper.map(dto, cursos.class);

        //docente por id
        docentes docente = repo.findById(dto.getDocente())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con id " + dto.getDocente()));
        curso.setDocente(docente);

        // Guardar
        cursos saved = service.save(curso);

        // entidad a DTO
        cursoDTO response = modelMapper.map(saved, cursoDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
        //return ResponseEntity.created(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<cursoDTO> update(@Valid @RequestBody cursoDTO dto, @PathVariable("id") Integer id) {
//        cursos cur = service.update(convertToEntity(dto),id);
//        cursoDTO newcur = convertToDtoWithLinks(cur);
//        return ResponseEntity.ok(newcur);

//        cursos curso = convertToEntity(dto);
//
//        // Buscar el docente y asignarlo al curso
//        docentes docente = repo.findById(dto.getDocente())
//                .orElseThrow(() -> new RuntimeException("Docente no encontrado con id " + dto.getDocente()));
//        curso.setDocente(docente);
//
//        // Llamar al servicio con el id del curso
//        cursos updatedCurso = service.update(curso, id);
//
//        // Convertir a DTO con links
//        cursoDTO response = convertToDtoWithLinks(updatedCurso);
        // Buscar curso existente
        cursos existe = service.findById(id);
        if (existe == null) {
            throw new RuntimeException("Curso no encontrado con id " + id);
        }

        // Actualizar datos con el mapper
        modelMapper.map(dto, existe);

        // Resolver el docente
        if (dto.getDocente() != 0) {
            docentes docente = repo.findById(dto.getDocente())
                    .orElseThrow(() -> new RuntimeException("Docente no encontrado con id " + dto.getDocente()));
            existe.setDocente(docente);
        }

        // Guardar cambios
        cursos updated = service.update(existe, id);

        // Convertir a DTO de respuesta
        cursoDTO response = modelMapper.map(updated, cursoDTO.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo Nivel 3
    private cursoDTO convertToDtoWithLinks(cursos curso) {
        cursoDTO dto = modelMapper.map(curso, cursoDTO.class);

        // Enlace "self" para el paciente
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(cursoController.class)
                .findById(curso.getId_curso())).withRel("Buscar por Id");

        // Enlace para actualizar el paciente
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(cursoController.class)
                .update(null, curso.getId_curso())).withRel("Actualizar");

        // Enlace para eliminar el paciente
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(cursoController.class)
                .delete(curso.getId_curso())).withRel("Borrar");

        // Enlace para listar todos los pacientes
        Link allPatientsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(cursoController.class)
                .findAll()).withRel("Todos los cursos");

        // Agregar los enlaces al DTO
        dto.add(selfLink, updateLink, deleteLink, allPatientsLink);

        return dto;
    }

    //Convertir de un dto a un modelo
    private cursos convertToEntity(cursoDTO dto) {
        return modelMapper.map(dto, cursos.class);
    }
}
