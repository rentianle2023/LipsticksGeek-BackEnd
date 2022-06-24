package fun.tianlefirstweb.www.product.lipstick;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("lipsticks")
public class LipstickController {

    private final LipstickService lipstickService;
    private final LipstickColorService lipstickColorService;

    public LipstickController(LipstickService lipstickService, LipstickColorService lipstickColorService) {
        this.lipstickService = lipstickService;
        this.lipstickColorService = lipstickColorService;
    }

    /**
     * 根据口红id获取口红
     */
    @GetMapping("/{lipstickId}")
    public ResponseEntity<Lipstick> getLipstick(@PathVariable Integer lipstickId){
        return ResponseEntity.ok(lipstickService.findById(lipstickId));
    }

    /**
     * 分页获取口红
     */
    @GetMapping("/{page}/{size}")
    public ResponseEntity<List<Lipstick>> findLipsticksWithPagination(
            @PathVariable int page,
            @PathVariable int size,
            HttpServletResponse response){
        Page<Lipstick> lipstickPage = lipstickService.findLipsticksWithPagination(page, size);
        if(page >= lipstickPage.getTotalPages()) {
            throw new EntityNotExistException("超出可查询的页数");
        }
        response.setHeader("X-Total-Count",String.valueOf(lipstickPage.getTotalElements()));
        return ResponseEntity.ok(lipstickPage.getContent());
    }

    /**
     * 根据色号获取口红
     */
    @GetMapping()
    public ResponseEntity<Lipstick> getLipstickByColor(@RequestParam Integer color){
        return ResponseEntity.ok(lipstickColorService.findById(color).getLipstick());
    }

    /**
     * 更新口红
     */
    @PutMapping()
    public ResponseEntity<?> updateLipstick(@RequestBody Lipstick lipstick){
        lipstickService.update(lipstick);
        return ResponseEntity.ok().build();
    }
}
