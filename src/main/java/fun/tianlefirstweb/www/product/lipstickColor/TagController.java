package fun.tianlefirstweb.www.product.lipstickColor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
