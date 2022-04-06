package fun.tianlefirstweb.www.product.brand;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<Brand>> getBrands(){
        return ResponseEntity.ok(brandService.getBrands());
    }

    @PostMapping
    public ResponseEntity<?> saveBrand(@RequestBody Brand brand){
        brandService.save(brand);
        return ResponseEntity.ok().build();
    }
}
