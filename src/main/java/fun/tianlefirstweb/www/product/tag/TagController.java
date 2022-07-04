package fun.tianlefirstweb.www.product.tag;

import fun.tianlefirstweb.www.product.brand.Brand;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorDTO;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorDetailsDTO;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    private final LipstickColorService lipstickColorService;

    public TagController(TagService tagService, LipstickColorService lipstickColorService) {
        this.tagService = tagService;
        this.lipstickColorService = lipstickColorService;
    }

    @GetMapping
    public ResponseEntity<List<Tag>> findAllTags(){
        return ResponseEntity.ok(tagService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> addTag(@RequestBody Tag tag){
        tagService.saveTag(tag);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{tagTitle}/colors")
    public ResponseEntity<List<LipstickColorDetailsDTO>> findColorsByTag(@PathVariable TagTitle tagTitle) {
        return ResponseEntity.ok(lipstickColorService.findLipstickColorDetailsByTagTitle(tagTitle));
    }
}
