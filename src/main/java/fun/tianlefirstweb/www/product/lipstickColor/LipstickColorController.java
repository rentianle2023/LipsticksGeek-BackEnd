package fun.tianlefirstweb.www.product.lipstickColor;

import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("colors")
public class LipstickColorController {

    private final LipstickColorService lipstickColorService;

    public LipstickColorController(LipstickColorService lipstickColorService) {
        this.lipstickColorService = lipstickColorService;
    }

    @GetMapping("/{colorId}/lipstick")
    public ResponseEntity<Lipstick> getLipstickByColorId(@PathVariable Integer colorId){
        return ResponseEntity.ok(lipstickColorService.findById(colorId).getLipstick());
    }

    @PutMapping("/{colorId}")
    public ResponseEntity<?> updateLipstickColor(@PathVariable Integer colorId,
                                                 @RequestBody LipstickColor color){
        lipstickColorService.updateLipstickColor(colorId,color.getName(),color.getHexColor());
        return ResponseEntity.ok().build();
    }
}
