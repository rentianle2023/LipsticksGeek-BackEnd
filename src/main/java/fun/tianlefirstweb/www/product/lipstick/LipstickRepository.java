package fun.tianlefirstweb.www.product.lipstick;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface LipstickRepository extends PagingAndSortingRepository<Lipstick,Integer> {

    List<Lipstick> findLipsticksByBrandIdAndActive(Integer brandId,boolean active);

    Boolean existsByNameAndActive(String lipstickName, boolean active);

    Optional<Lipstick> findByNameAndActive(String lipstickName, boolean active);

    Optional<Lipstick> findByIdAndActive(Integer id, boolean active);

    boolean existsByIdAndActive(Integer id, boolean active);

    Page<Lipstick> findAllByActive(boolean active, Pageable page);

    List<Lipstick> findAllByActive(boolean active);
}
