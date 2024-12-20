package tacos.data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import tacos.Taco;

import java.util.List;

public interface TacoRepository extends CrudRepository<Taco, Long> {
    List<Taco> findAll(Pageable pageable);
}