package fun.tianlefirstweb.www.product.brand;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends CrudRepository<Brand,Integer> {

    public boolean existsBrandByName(String name);

    public Optional<Brand> findBrandByName(String name);

    public List<Brand> findAll();
}
