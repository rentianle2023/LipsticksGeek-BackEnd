package fun.tianlefirstweb.www.product.brand;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstick.LipstickService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
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
        return ResponseEntity.ok(brandService.findBrands());
    }

    @GetMapping("/{brandId}/lipsticks")
    public ResponseEntity<List<Lipstick>> getLipsticks(@PathVariable Integer brandId){
        return ResponseEntity.ok(lipstickService.findByBrandId(brandId));
    }

    @PostMapping
    public ResponseEntity<?> saveBrand(@RequestBody Brand brand){
        brandService.save(brand);
        return ResponseEntity.ok().build();
    }
}
