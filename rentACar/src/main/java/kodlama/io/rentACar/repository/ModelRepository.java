package kodlama.io.rentACar.repository;

import kodlama.io.rentACar.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    boolean existsByName(String name);

    List<Model> findAllByOrderByNameAsc();

    List<Model> findAllByOrderByNameDesc();
}
