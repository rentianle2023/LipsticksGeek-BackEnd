package fun.tianlefirstweb.www.search.lipstickColor;

import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorRepository;
import fun.tianlefirstweb.www.search.SearchRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search/colors")
public class LipstickColorSearchController {

    private final LipstickColorRepository lipstickColorRepository;
    private final LipstickColorSearchRepository lipstickColorSearchRepository;
    private final LipstickColorSearchService lipstickColorSearchService;

    public LipstickColorSearchController(LipstickColorRepository lipstickColorRepository, LipstickColorSearchRepository lipstickColorSearchRepository, LipstickColorSearchService lipstickColorSearchService) {
        this.lipstickColorRepository = lipstickColorRepository;
        this.lipstickColorSearchRepository = lipstickColorSearchRepository;
        this.lipstickColorSearchService = lipstickColorSearchService;
    }


    @PostMapping
    private ResponseEntity<?> refresh(){
        //TODO
        lipstickColorSearchRepository.deleteAll();
        List<LipstickColorSearch> insertList = new ArrayList<>();
        lipstickColorRepository.findAll()
                .forEach(color -> insertList.add(new LipstickColorSearch(
                        String.valueOf(color.getId()),
                        color.getName(),
                        color.getHexColor()
                )));
        System.out.println(insertList.size());
        lipstickColorSearchRepository.saveAll(insertList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    private ResponseEntity<List<LipstickColorSearch>> search(@RequestBody SearchRequestDTO requestDTO){
        return ResponseEntity.ok(lipstickColorSearchService.search(requestDTO));
    }

    @GetMapping("/search/{hexColor}")
    private ResponseEntity<List<LipstickColorSearch>> search(@PathVariable String hexColor){
        return ResponseEntity.ok(lipstickColorSearchService.search(hexColor));
    }
}
