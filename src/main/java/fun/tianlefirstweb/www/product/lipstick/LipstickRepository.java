package fun.tianlefirstweb.www.product.lipstick;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LipstickRepository extends CrudRepository<Lipstick,Integer> {

    List<Lipstick> findLipsticksByBrandId(Integer brandId);

    Boolean existsByName(String lipstickName);

    Optional<Lipstick> findByName(String lipstickName);
}
