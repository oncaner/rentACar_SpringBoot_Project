package kodlama.io.rentACar.repository;

import kodlama.io.rentACar.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    boolean existsByPlate(String name);

    List<Car> findAllByModelId(int id);

    List<Car> findAllByOrderByDailyPriceAsc();

    List<Car> findAllByOrderByDailyPriceDesc();

    @Query("SELECT c FROM Car c "
            + "JOIN c.model m "
            + "JOIN m.brand b "
            + "WHERE b.id = :brandId")
    List<Car> findAllByBrandId(@Param("brandId") int id);

}
