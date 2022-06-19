package fun.tianlefirstweb.www.product.lipstickColor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Integer> {

    Optional<Tag> findByTitle(TagTitle title);
}
