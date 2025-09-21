package brionesrojas.service.Implements;

import brionesrojas.model.estudiantes;
import brionesrojas.repository.IEstudianteRepository;
import brionesrojas.repository.IGenericRepository;
import brionesrojas.service.IEstudianteService;
import brionesrojas.service.IGenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class estudianteService extends GenericService<estudiantes, Integer> implements IEstudianteService {
    private final IEstudianteRepository repo;

    @Override
    protected IGenericRepository<estudiantes, Integer> getRepo() {
        return repo;
    }
}
