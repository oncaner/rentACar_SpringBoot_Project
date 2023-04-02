package kodlama.io.rentACar.repository;

import kodlama.io.rentACar.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Integer> {
    boolean existsByName(String name);
}
