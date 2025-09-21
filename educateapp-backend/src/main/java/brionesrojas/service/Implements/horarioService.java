package brionesrojas.service.Implements;

import brionesrojas.model.horarios;
import brionesrojas.repository.IGenericRepository;
import brionesrojas.repository.IHorarioRepository;
import brionesrojas.service.IGenericService;
import brionesrojas.service.IHorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class horarioService extends GenericService<horarios, Integer> implements IHorarioService {
    private final IHorarioRepository repoh;

    @Override
    protected IGenericRepository<horarios, Integer> getRepo() {
        return repoh;
    }
}
