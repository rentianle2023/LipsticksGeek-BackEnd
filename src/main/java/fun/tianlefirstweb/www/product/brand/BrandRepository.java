package fun.tianlefirstweb.www.product.brand;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends CrudRepository<Brand,Integer> {

    boolean existsBrandByName(String name);

    Optional<Brand> findBrandByName(String name);

    List<Brand> findAll();
}
