package fun.tianlefirstweb.www.product.lipstickColor;

import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstick.LipstickDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("colors")
public class LipstickColorController {

    private final LipstickColorService lipstickColorService;
    private final TagService tagService;

    public LipstickColorController(LipstickColorService lipstickColorService,
                                   TagService tagService) {
        this.lipstickColorService = lipstickColorService;
        this.tagService = tagService;
    }

    @GetMapping("/{colorId}/lipstick")
    public ResponseEntity<LipstickDTO> getLipstickByColorId(@PathVariable Integer colorId){
        LipstickDTO lipstickDTO = new LipstickDTO(lipstickColorService.findById(colorId).getLipstick());
        return ResponseEntity.ok(lipstickDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateLipstickColor(@RequestBody LipstickColor color){
        lipstickColorService.updateLipstickColor(color);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{colorId}/tags")
    public ResponseEntity<List<Tag>> findTagsByColor(@PathVariable Integer colorId) {
        LipstickColor color = lipstickColorService.findById(colorId);
        return ResponseEntity.ok(color.getTags());
    }

    @PostMapping("/{colorId}/tag/{tagId}")
    public ResponseEntity<?> bindColorWithTag(@PathVariable Integer colorId,
                                              @PathVariable Integer tagId){
        LipstickColor color = lipstickColorService.findById(colorId);
        Tag tag = tagService.findById(tagId);
        color.getTags().add(tag);
        lipstickColorService.save(color);
        return ResponseEntity.ok().build();
    }
}
