package fun.tianlefirstweb.www.product.lipstick;

import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lipsticks")
public class LipstickController {

    private final LipstickService lipstickService;
    private final LipstickColorService lipstickColorService;

    public LipstickController(LipstickService lipstickService, LipstickColorService lipstickColorService) {
        this.lipstickService = lipstickService;
        this.lipstickColorService = lipstickColorService;
    }

    @GetMapping("/{lipstickId}")
    public Lipstick getLipstick(@PathVariable Integer lipstickId){
        return lipstickService.getById(lipstickId);
    }

    @GetMapping()
    public ResponseEntity<Lipstick> getLipstickByColor(@RequestParam Integer color){
        return ResponseEntity.ok(lipstickColorService.findById(color).getLipstick());
    }

    @PutMapping("/{lipstickId}")
    public ResponseEntity<?> updateLipstick(@PathVariable Integer lipstickId,
                                            @RequestBody Lipstick lipstick){
        lipstickService.update(lipstickId,lipstick.getName(),lipstick.getPrice(),lipstick.getImageUrl());
        return ResponseEntity.ok().build();
    }
}
