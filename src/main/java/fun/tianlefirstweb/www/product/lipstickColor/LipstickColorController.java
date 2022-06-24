package fun.tianlefirstweb.www.product.lipstickColor;

import fun.tianlefirstweb.www.product.lipstick.LipstickDTO;
import fun.tianlefirstweb.www.product.lipstick.LipstickService;
import fun.tianlefirstweb.www.product.tag.Tag;
import fun.tianlefirstweb.www.product.tag.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("colors")
public class LipstickColorController {

    private final LipstickColorService lipstickColorService;
    private final LipstickService lipstickService;
    private final TagService tagService;

    public LipstickColorController(LipstickColorService lipstickColorService,
                                   LipstickService lipstickService,
                                   TagService tagService) {
        this.lipstickColorService = lipstickColorService;
        this.lipstickService = lipstickService;
        this.tagService = tagService;
    }

    /**
     * 根据色号id获取口红（仅返回口红信息，不包含其关联色号）
     */
    @GetMapping("/{colorId}/lipstick")
    public ResponseEntity<LipstickDTO> getLipstickByColorId(@PathVariable Integer colorId){
        LipstickDTO lipstickDTO = new LipstickDTO(lipstickService.findLipstickByColorId(colorId));
        return ResponseEntity.ok(lipstickDTO);
    }

    /**
     * 更新色号（包含色号的名字，颜色，tags）
     */
    @PutMapping
    public ResponseEntity<?> updateLipstickColor(@RequestBody LipstickColor color){
        lipstickColorService.updateLipstickColor(color);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据色号id获取所有的tags
     */
    @GetMapping("/{colorId}/tags")
    public ResponseEntity<List<Tag>> findTagsByColor(@PathVariable Integer colorId) {
        LipstickColor color = lipstickColorService.findById(colorId);
        return ResponseEntity.ok(color.getTags());
    }

    /**
     * 根据色号id获取色号的细节信息（包含品牌信息，口红信息，和色号信息）
     */
    @GetMapping("/{colorId}/details")
    public ResponseEntity<LipstickColorDetailsDTO> findLipstickDetailsById(@PathVariable Integer colorId) {
        return ResponseEntity.ok(
                lipstickColorService.findColorDetailsById(colorId));
    }
}
