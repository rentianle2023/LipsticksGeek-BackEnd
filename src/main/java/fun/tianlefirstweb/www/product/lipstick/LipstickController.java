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

    @GetMapping("/{lipstickId}")
    public ResponseEntity<Lipstick> getLipstick(@PathVariable Integer lipstickId){
        return ResponseEntity.ok(lipstickService.findById(lipstickId));
    }

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

    @GetMapping()
    public ResponseEntity<Lipstick> getLipstickByColor(@RequestParam Integer color){
        return ResponseEntity.ok(lipstickColorService.findById(color).getLipstick());
    }

    @PutMapping()
    public ResponseEntity<?> updateLipstick(@RequestBody Lipstick lipstick){
        lipstickService.update(lipstick);
        return ResponseEntity.ok().build();
    }
}
