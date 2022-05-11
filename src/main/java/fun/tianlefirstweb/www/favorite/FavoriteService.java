package fun.tianlefirstweb.www.favorite;

import fun.tianlefirstweb.www.exception.EntityAlreadyExistException;
import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorService;
import fun.tianlefirstweb.www.user.ApplicationUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final LipstickColorService lipstickColorService;

    public FavoriteService(FavoriteRepository favoriteRepository, LipstickColorService lipstickColorService) {
        this.favoriteRepository = favoriteRepository;
        this.lipstickColorService = lipstickColorService;
    }

    public FavoriteResponseDTO addFavorite(ApplicationUser user, LipstickColor color) {
        if(favoriteRepository.existsByUserAndColor(user,color)){
            throw new EntityAlreadyExistException("收藏已存在，请勿重复收藏");
        }
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        favoriteRepository.save(new Favorite(user,color,timestamp));
        return new FavoriteResponseDTO(color.getId(),color.getName(),color.getHexColor(),timestamp);
    }

    @Transactional
    public void removeFavorite(ApplicationUser user, LipstickColor color){
        if(!favoriteRepository.existsByUserAndColor(user,color)){
            throw new EntityNotExistException("不存在该收藏，无法删除");
        }
        favoriteRepository.removeByUserAndColor(user,color);
    }

    public List<FavoriteResponseDTO> findFavoriteColors(ApplicationUser user) {
        return favoriteRepository.findByUserId(user.getId(), PageRequest.of(0, 20))
                .stream().map(favorite -> {
                    LipstickColor color = favorite.getColor();
                    return new FavoriteResponseDTO(color.getId(),color.getName(),color.getHexColor(),favorite.getCreateTime());
                }).collect(Collectors.toList());
    }

    public Integer countFavoriteColors(ApplicationUser user){
        return favoriteRepository.countByUser(user);
    }
}
