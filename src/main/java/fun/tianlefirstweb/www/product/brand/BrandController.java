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
import java.sql.ResultSet;
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

    /**
     * 获得所有的Brand信息，展示在百科的第一个界面
     */
    @GetMapping
    public ResponseEntity<List<Brand>> getBrands(){
        return ResponseEntity.ok(brandService.findBrands());
    }

    /**
     * 根据brandId获取该品怕下的所有口红+色号
     * TODO 避免Eager获取色号，不是很好的方式
     */
    @GetMapping("/{brandId}/lipsticks")
    public ResponseEntity<List<Lipstick>> getLipsticks(@PathVariable Integer brandId){
        return ResponseEntity.ok(lipstickService.findByBrandId(brandId));
    }

    /**
     * 保存/更新品牌信息，一个没有被调用过的接口
     */
    @PostMapping
    public ResponseEntity<?> saveBrand(@RequestBody Brand brand){
        brandService.save(brand);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Integer brandId) {
        return ResponseEntity.ok(brandService.findById(brandId));
    }
}
