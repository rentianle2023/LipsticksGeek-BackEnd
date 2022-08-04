package fun.tianlefirstweb.www.product.brand;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


public interface BrandRepository extends JpaRepository<Brand,Integer> {

    boolean existsBrandByName(String name);

    Optional<Brand> findBrandByName(String name);

    @Query(value = "SELECT b.name brandName, COUNT(l.id) lipstickCount\n" +
            "FROM `brand` b\n" +
            "LEFT JOIN lipstick l\n" +
            "ON b.id = l.brand_id \n" +
            "GROUP BY b.id", nativeQuery = true)
    List<BrandLipstickCount> findBrandAndLipstickCount();

    interface BrandLipstickCount {

        String getBrandName();
        Integer getLipstickCount();
    }
}
