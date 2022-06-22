package fun.tianlefirstweb.www.product.lipstickColor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LipstickColorRepository extends CrudRepository<LipstickColor, Integer> {
}
