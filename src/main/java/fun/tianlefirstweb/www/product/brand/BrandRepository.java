package fun.tianlefirstweb.www.product.brand;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Integer> {

    boolean existsBrandByName(String name);

    Optional<Brand> findBrandByName(String name);
}
