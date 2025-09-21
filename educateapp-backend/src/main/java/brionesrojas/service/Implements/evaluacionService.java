package brionesrojas.service.Implements;

import brionesrojas.model.evaluaciones;
import brionesrojas.repository.IEvaluacionRepository;
import brionesrojas.repository.IGenericRepository;
import brionesrojas.service.IEvaluacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class evaluacionService extends GenericService<evaluaciones, Integer> implements IEvaluacionService {
    private final IEvaluacionRepository repoev;

    @Override
    protected IGenericRepository<evaluaciones, Integer> getRepo() {
        return repoev;
    }
}
