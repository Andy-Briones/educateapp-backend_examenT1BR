package brionesrojas.controller;

import brionesrojas.dto.cursoDTO;
import brionesrojas.dto.estudianteDTO;
import brionesrojas.dto.matriculaDTO;
import brionesrojas.model.cursos;
import brionesrojas.model.docentes;
import brionesrojas.model.estudiantes;
import brionesrojas.model.matriculas;
import brionesrojas.repository.ICursoRepository;
import brionesrojas.repository.IEstudianteRepository;
import brionesrojas.service.ICursoService;
import brionesrojas.service.IEstudianteService;
import brionesrojas.service.IMatriculaService;
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
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class matriculaController {
    private final IMatriculaService service;
    private final ModelMapper modelMapper;
    private final ICursoRepository repocurso;
    private final IEstudianteRepository repoes;

    @GetMapping
    public ResponseEntity<List<matriculaDTO>> findAll(){
        List<matriculaDTO> list = service.findAll().stream().map(this::convertToDtoWithLinks).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<matriculaDTO> findById(@PathVariable("id") Integer id){
        matriculaDTO matricula = convertToDtoWithLinks(service.findById(id));
        return ResponseEntity.ok(matricula);
    }

    @PostMapping
    public ResponseEntity<matriculaDTO> save(@Valid @RequestBody matriculaDTO dto) throws Exception {
//        matriculas mat = service.save(convertToEntity(dto));
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mat.getId_matricula()).toUri();
//        return ResponseEntity.created(location).build();

        //Matricula con Estudiante y Curso
        matriculas matricula = modelMapper.map(dto, matriculas.class);

        //estudiante por id
        estudiantes estudiante = repoes.findById(dto.getEstudiante())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id " + dto.getEstudiante()));
        matricula.setEstudiante(estudiante);
        //curso por id
        cursos curso = repocurso.findById(dto.getCurso())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id " + dto.getCurso()));
        matricula.setCurso(curso);

        // Guardar
        matriculas saved = service.save(matricula);
        // entidad a DTO
        matriculaDTO response = modelMapper.map(saved, matriculaDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<matriculaDTO> update(@Valid @RequestBody matriculaDTO dto, @PathVariable("id") Integer id) {
//        matriculas mat = service.update(convertToEntity(dto),id);
//        matriculaDTO newmat = convertToDtoWithLinks(mat);
//        return ResponseEntity.ok(newmat);
        matriculas existe = service.findById(id);
        if (existe == null) {
            throw new RuntimeException("matricula no encontrado con id " + id);
        }
        // Actualizar datos con el mapper
        modelMapper.map(dto, existe);
        // Resolver el estudiante
        if (dto.getEstudiante() != 0) {
            estudiantes estudiante = repoes.findById(dto.getEstudiante())
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id " + dto.getEstudiante()));
            existe.setEstudiante(estudiante);
        }
        //Resolver el curso
        if(dto.getCurso() != 0) {
            cursos curso = repocurso.findById(dto.getCurso())
                    .orElseThrow(()-> new RuntimeException("Curso no encontrado con id " + dto.getCurso()));
            existe.setCurso(curso);
        }
        // Guardar cambios
        matriculas updated = service.update(existe, id);
        // Convertir a DTO de respuesta
        matriculaDTO response = modelMapper.map(updated, matriculaDTO.class);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    //Metodo Nivel 3
    private matriculaDTO convertToDtoWithLinks(matriculas matricula) {
        matriculaDTO dto = modelMapper.map(matricula, matriculaDTO.class);

        // Enlace "self" para el paciente
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(matriculaController.class)
                .findById(matricula.getId_matricula())).withRel("Buscar por Id");

        // Enlace para actualizar el paciente
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(matriculaController.class)
                .update(null, matricula.getId_matricula())).withRel("Actualizar");

        // Enlace para eliminar el paciente
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(matriculaController.class)
                .delete(matricula.getId_matricula())).withRel("Borrar");

        // Enlace para listar todos los pacientes
        Link allPatientsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(matriculaController.class)
                .findAll()).withRel("Todas las matriculas");

        // Agregar los enlaces al DTO
        dto.add(selfLink, updateLink, deleteLink, allPatientsLink);

        return dto;
    }

    //Convertir de un dto a un modelo
    private matriculas convertToEntity(matriculaDTO dto) {
        return modelMapper.map(dto, matriculas.class);
    }
}
