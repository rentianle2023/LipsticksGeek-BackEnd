package fun.tianlefirstweb.www.product.lipstickColor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LipstickColorRepository extends CrudRepository<LipstickColor, Integer> {

    @Query(value = "SELECT c.* FROM lipstick_color c JOIN color_tag ct ON c.id = ct.color_id JOIN tag t ON t.id = ct.tag_id WHERE t.title = :title",nativeQuery = true)
    List<LipstickColor> findLipstickColorsByTag(String title);

    Optional<LipstickColor> findTopByOrderByIdDesc();
}
