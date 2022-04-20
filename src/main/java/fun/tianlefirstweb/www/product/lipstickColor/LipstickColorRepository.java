package fun.tianlefirstweb.www.product.lipstickColor;

import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LipstickColorRepository extends CrudRepository<LipstickColor,Integer> {

    Optional<List<LipstickColor>> findLipstickColorByLipstickId(Integer lipstickId);

    Optional<Lipstick> findLipstickById(Integer colorId);
}
