package fun.tianlefirstweb.www.product.lipstick;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lipsticks")
public class LipstickController {

    private final LipstickService lipstickService;

    public LipstickController(LipstickService lipstickService) {
        this.lipstickService = lipstickService;
    }


    @GetMapping("/{lipstickId}")
    public Lipstick getLipstick(@PathVariable Integer lipstickId){
        return lipstickService.getById(lipstickId);
    }

    @PutMapping("/{lipstickId}")
    public ResponseEntity<?> updateLipstick(@PathVariable Integer lipstickId,
                                            @RequestBody Lipstick lipstick){
        lipstickService.update(lipstickId,lipstick.getName(),lipstick.getPrice(),lipstick.getImageUrl());
        return ResponseEntity.ok().build();
    }
}
