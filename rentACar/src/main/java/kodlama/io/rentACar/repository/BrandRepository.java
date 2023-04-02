package kodlama.io.rentACar.repository;

import kodlama.io.rentACar.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> { // Integer --> Primary key is a integer.
    boolean existsByName(String name);
}