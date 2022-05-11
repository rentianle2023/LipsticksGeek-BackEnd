package fun.tianlefirstweb.www.favorite;

import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorService;
import fun.tianlefirstweb.www.user.ApplicationUser;
import fun.tianlefirstweb.www.user.FavoriteColorDTO;
import fun.tianlefirstweb.www.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    private final LipstickColorService colorService;

    public FavoriteController(FavoriteService favoriteService,
                              UserService userService,
                              LipstickColorService colorService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
        this.colorService = colorService;
    }

    @PostMapping()
    public ResponseEntity<FavoriteResponseDTO> addFavorite(Authentication authentication, @RequestBody FavoriteColorDTO colorDTO){
        ApplicationUser user = userService.findByUsername(authentication.getName());
        LipstickColor color = colorService.findById(colorDTO.getColorId());
        return ResponseEntity.ok(favoriteService.addFavorite(user,color));
    }

    @GetMapping()
    public ResponseEntity<List<FavoriteResponseDTO>> getFavorites(Authentication authentication){
        ApplicationUser user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(favoriteService.findFavoriteColors(user));
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countFavorites(Authentication authentication){
        ApplicationUser user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(favoriteService.countFavoriteColors(user));
    }

    @DeleteMapping()
    public ResponseEntity<?> removeFavorite(Authentication authentication, @RequestBody FavoriteColorDTO colorDTO){
        ApplicationUser user = userService.findByUsername(authentication.getName());
        LipstickColor color = colorService.findById(colorDTO.getColorId());
        favoriteService.removeFavorite(user,color);
        return ResponseEntity.ok().build();
    }
}
