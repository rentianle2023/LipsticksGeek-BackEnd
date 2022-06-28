package fun.tianlefirstweb.www.product.lipstick;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LipstickRepository extends PagingAndSortingRepository<Lipstick,Integer> {

    @Query("SELECT DISTINCT l FROM Lipstick l LEFT JOIN FETCH l.colors WHERE l.brand.id = :brandId AND l.active = :active")
    List<Lipstick> findLipsticksWithColorsByBrandIdAndActive(Integer brandId,boolean active);

    Boolean existsByName(String lipstickName);

    @Query("SELECT l FROM Lipstick l LEFT JOIN FETCH l.colors WHERE l.active = :active")
    List<Lipstick> findAllByActive(boolean active);

    @Query("SELECT l FROM Lipstick l LEFT JOIN FETCH l.colors WHERE l.id = :id")
    Optional<Lipstick> findLipstickWithColorsById(Integer id);

    @Query("SELECT l FROM Lipstick l LEFT JOIN FETCH l.colors WHERE l.name = :name")
    Optional<Lipstick> findLipstickWithColorsByName(String name);

    @Query(value = "SELECT l FROM Lipstick l LEFT JOIN FETCH l.colors",
            countQuery = "SELECT count(l) FROM Lipstick l")
    Page<Lipstick> findLipstickWithColors(Pageable pageable);

    @Query(value = "SELECT * from lipstick l where l.id = (SELECT lipstick_id from lipstick_color c where c.id = :colorId)",nativeQuery = true)
    Optional<Lipstick> findLipstickByColorId(Integer colorId);

    @Modifying
    @Transactional
    @Query("UPDATE Lipstick l SET l.name = :name, l.price = :price, l.imageUrl = :imageUrl WHERE l.id = :id")
    void update(Integer id, String name, String price, String imageUrl);
}
