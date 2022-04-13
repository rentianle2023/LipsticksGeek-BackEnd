package fun.tianlefirstweb.www.product.lipstickColor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("colors")
public class LipstickColorController {

    private final LipstickColorService lipstickColorService;

    public LipstickColorController(LipstickColorService lipstickColorService) {
        this.lipstickColorService = lipstickColorService;
    }

    @GetMapping("/{lipstickId}")
    public ResponseEntity<List<LipstickColor>> getLipstickColors(@PathVariable Integer lipstickId){
        return ResponseEntity.ok(lipstickColorService.getByLipstickId(lipstickId));
    }

    @PutMapping("/{lipstickColorId}")
    public ResponseEntity<?> updateLipstickColor(@PathVariable Integer lipstickColorId,
                                                 @RequestBody LipstickColor lipstickColor){
        lipstickColorService.updateLipstickColor(lipstickColorId,lipstickColor.getName(),lipstickColor.getHexColor());
        return ResponseEntity.ok().build();
    }
}
