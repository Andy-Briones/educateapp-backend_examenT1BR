package brionesrojas.service.Implements;

import brionesrojas.model.docentes;
import brionesrojas.repository.IDocenteRepository;
import brionesrojas.repository.IGenericRepository;
import brionesrojas.service.IDocenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class docenteService extends GenericService<docentes, Integer> implements IDocenteService {
    private final IDocenteRepository repod;

    @Override
    protected IGenericRepository<docentes, Integer> getRepo() {
        return repod;
    }
}
