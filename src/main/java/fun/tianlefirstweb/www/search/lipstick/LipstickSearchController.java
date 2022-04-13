package fun.tianlefirstweb.www.search.lipstick;

import fun.tianlefirstweb.www.search.SearchRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search/lipsticks")
public class LipstickSearchController {

    private final LipstickSearchService lipstickSearchService;

    public LipstickSearchController(LipstickSearchService lipstickSearchService) {
        this.lipstickSearchService = lipstickSearchService;
    }

    @PostMapping
    public ResponseEntity<?> saveLipstickDocument(@RequestBody LipstickSearch lipstickSearch){
        lipstickSearchService.saveLipstickDocument(lipstickSearch);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<LipstickSearch> searchLipstick(@PathVariable String id){
        return ResponseEntity.ok(lipstickSearchService.searchLipstick(id));
    }

    @PostMapping("/search")
    public ResponseEntity<List<LipstickSearch>> searchLipsticks(@RequestBody SearchRequestDTO requestDTO){
        return ResponseEntity.ok(lipstickSearchService.searchLipsticks(requestDTO));
    }

//    @GetMapping("/refresh")
//    public ResponseEntity<?> refresh(){
//        repository.deleteAll();
//        List<LipstickSearch> list = new ArrayList<>();
//        lipstickRepository.findAll()
//                .forEach(lipstick -> {
//                    var search = new LipstickSearch();
//                    search.setId(String.valueOf(lipstick.getId()));
//                    search.setName(lipstick.getName());
//                    list.add(search);
//                });
//        System.out.println(list);
//        repository.saveAll(list);
//        return ResponseEntity.ok().build();
//    }
}
