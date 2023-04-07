package kodlama.io.rentACar.repository;

import kodlama.io.rentACar.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> { // Integer --> Primary key is a integer.
    boolean existsByName(String name);

    List<Brand> findAllByOrderByNameDesc();

    List<Brand> findAllByOrderByNameAsc();
}