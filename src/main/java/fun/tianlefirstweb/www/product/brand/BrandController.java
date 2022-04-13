package fun.tianlefirstweb.www.product.brand;

import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstick.LipstickService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("brands")
public class BrandController {

    private final BrandService brandService;
    private final LipstickService lipstickService;

    public BrandController(BrandService brandService, LipstickService lipstickService) {
        this.brandService = brandService;
        this.lipstickService = lipstickService;
    }

    @GetMapping
    public ResponseEntity<List<Brand>> getBrands(){
        return ResponseEntity.ok(brandService.getBrands());
    }

    @GetMapping("/{brandId}/lipsticks")
    public List<Lipstick> getLipsticks(@PathVariable Integer brandId){
        return lipstickService.getByBrandId(brandId);
    }

    @PostMapping
    public ResponseEntity<?> saveBrand(@RequestBody Brand brand){
        brandService.save(brand);
        return ResponseEntity.ok().build();
    }
}
