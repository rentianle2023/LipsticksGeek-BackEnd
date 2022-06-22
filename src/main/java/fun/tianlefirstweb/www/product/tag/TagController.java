package fun.tianlefirstweb.www.product.tag;

import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
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
    public ResponseEntity<List<LipstickColorDTO>> findColorsByTag(@PathVariable TagTitle tagTitle) {
        Tag tag = tagService.findByTitle(tagTitle);
        return ResponseEntity.ok(tag.getColors()
                .stream()
                .map(LipstickColorDTO::new)
                .collect(Collectors.toList()));
    }
}
