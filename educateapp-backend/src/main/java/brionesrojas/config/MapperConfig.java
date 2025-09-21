package brionesrojas.config;

import brionesrojas.dto.cursoDTO;
import brionesrojas.dto.matriculaDTO;
import brionesrojas.model.cursos;
import brionesrojas.model.matriculas;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        //Mapear docente con curso
        modelMapper.typeMap(cursos.class, cursoDTO.class)
                .addMappings(mapper -> {
            mapper.map(src -> src.getDocente().getId_docente(), cursoDTO::setDocente);
        });
        modelMapper.typeMap(cursoDTO.class, cursos.class)
                .addMappings(mapper -> {
                    mapper.skip(cursos::setDocente);
                });

        //Mapear estudiante con matricula
        modelMapper.typeMap(matriculas.class, matriculaDTO.class)
                .addMappings(mapper -> {
                   mapper.map(src->src.getEstudiante().getId_estudiante(), matriculaDTO::setEstudiante);
                });
        modelMapper.typeMap(matriculaDTO.class, matriculas.class)
                .addMappings(mapper -> {
                    mapper.skip(matriculas::setEstudiante);
                });

        //Mapear curso con matricula
        modelMapper.typeMap(matriculas.class, matriculaDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src ->src.getCurso().getId_curso(), matriculaDTO::setCurso);
                });
        modelMapper.typeMap(matriculaDTO.class, matriculas.class)
                .addMappings(mapper -> {
                    mapper.skip(matriculas::setCurso);
                });

        return modelMapper;
    }
}
