package brionesrojas.service.Implements;

import brionesrojas.model.matriculas;
import brionesrojas.repository.IGenericRepository;
import brionesrojas.repository.IMatriculaRepository;
import brionesrojas.service.IMatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class matriculaService extends GenericService<matriculas, Integer> implements IMatriculaService {
    private final IMatriculaRepository repom;

    @Override
    protected IGenericRepository<matriculas, Integer> getRepo() {
        return repom;
    }
}
