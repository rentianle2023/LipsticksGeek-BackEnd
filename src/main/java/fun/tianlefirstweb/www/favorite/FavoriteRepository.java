package fun.tianlefirstweb.www.favorite;

import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import fun.tianlefirstweb.www.user.ApplicationUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends CrudRepository<Favorite,Integer> {

    boolean existsByUserAndColor(ApplicationUser user, LipstickColor color);

    @Query("select f from Favorite f where f.user.id = :userId")
    List<Favorite> findByUserId(Integer userId, Pageable pageable);

    void removeByUserAndColor(ApplicationUser user, LipstickColor color);

    Integer countByUser(ApplicationUser user);
}
