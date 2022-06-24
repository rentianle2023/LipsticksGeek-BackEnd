package fun.tianlefirstweb.www.product.tag;

import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Integer> {

    Optional<Tag> findByTitle(TagTitle title);

    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.colors c WHERE t.title = :title")
    Tag findTagWithColorsByTitle(TagTitle title);
}
