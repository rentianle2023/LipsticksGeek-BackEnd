package fun.tianlefirstweb.www.product.lipstick;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lipsticks")
public class LipstickController {

    private final LipstickService lipstickService;

    public LipstickController(LipstickService lipstickService) {
        this.lipstickService = lipstickService;
    }

    @GetMapping("/{brandId}")
    public List<Lipstick> getLipsticks(@PathVariable Integer brandId){
        return lipstickService.getLipsticks(brandId);
    }

    @PutMapping("/{lipstickId}")
    public ResponseEntity<?> updateLipstick(@PathVariable Integer lipstickId,
                                            @RequestBody Lipstick lipstick){
        lipstickService.updateLipstick(lipstickId,lipstick.getName(),lipstick.getPrice(),lipstick.getImageUrl());
        return ResponseEntity.ok().build();
    }
}
