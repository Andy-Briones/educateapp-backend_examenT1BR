package brionesrojas.service.Implements;

import brionesrojas.model.cursos;
import brionesrojas.repository.ICursoRepository;
import brionesrojas.repository.IGenericRepository;
import brionesrojas.repository.IMatriculaRepository;
import brionesrojas.service.ICursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class cursoService extends GenericService<cursos, Integer> implements ICursoService {
    private final ICursoRepository repoc;

    @Override
    protected IGenericRepository<cursos, Integer> getRepo() {
        return repoc;
    }
}
