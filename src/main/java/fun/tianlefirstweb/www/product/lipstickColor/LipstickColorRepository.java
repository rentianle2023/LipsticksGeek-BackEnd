package fun.tianlefirstweb.www.product.lipstickColor;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LipstickColorRepository extends CrudRepository<LipstickColor,Integer> {

    Optional<List<LipstickColor>> findLipstickColorByLipstickId(Integer lipstickId);
}
