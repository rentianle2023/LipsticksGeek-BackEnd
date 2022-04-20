package fun.tianlefirstweb.www.search;

import fun.tianlefirstweb.www.product.lipstick.LipstickRepository;
import fun.tianlefirstweb.www.search.lipstick.LipstickSearch;
import fun.tianlefirstweb.www.search.lipstick.LipstickSearchRepository;
import fun.tianlefirstweb.www.search.lipstick.LipstickSearchService;
import fun.tianlefirstweb.www.search.lipstickColor.LipstickColorSearch;
import fun.tianlefirstweb.www.search.lipstickColor.LipstickColorSearchRepository;
import fun.tianlefirstweb.www.search.lipstickColor.LipstickColorSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final LipstickSearchService lipstickSearchService;
    private final LipstickColorSearchService lipstickColorSearchService;
    private final LipstickRepository lipstickRepository;
    private final LipstickSearchRepository lipstickSearchRepository;
    private final LipstickColorSearchRepository lipstickColorSearchRepository;

    public SearchController(LipstickSearchService lipstickSearchService,
                            LipstickColorSearchService lipstickColorSearchService,
                            LipstickRepository lipstickRepository,
                            LipstickSearchRepository lipstickSearchRepository,
                            LipstickColorSearchRepository lipstickColorSearchRepository) {
        this.lipstickSearchService = lipstickSearchService;
        this.lipstickColorSearchService = lipstickColorSearchService;
        this.lipstickRepository = lipstickRepository;
        this.lipstickSearchRepository = lipstickSearchRepository;
        this.lipstickColorSearchRepository = lipstickColorSearchRepository;
    }

    @PostMapping
    public ResponseEntity<?> saveLipstickDocument(@RequestBody LipstickSearch lipstickSearch) {
        lipstickSearchService.saveLipstickDocument(lipstickSearch);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<LipstickSearch> searchLipstick(@PathVariable String id) {
        return ResponseEntity.ok(lipstickSearchService.searchLipstick(id));
    }

    @PostMapping("/all")
    public ResponseEntity<Map<String, List<SearchResponseDTO>>> searchLipsticks(@RequestBody SearchRequestDTO requestDTO) {
        List<LipstickSearch> lipsticks = lipstickSearchService.searchLipsticks(requestDTO);
        List<LipstickColorSearch> colors = lipstickColorSearchService.search(requestDTO);
        Map<String, List<SearchResponseDTO>> map = new HashMap<>();
        lipsticks.forEach(lipstick -> {
            map.computeIfAbsent(lipstick.getBrandName(), i -> new ArrayList<>());
            map.get(lipstick.getBrandName()).add(new SearchResponseDTO(lipstick.getId(), lipstick.getName(), false,null));
        });
        colors.forEach(color -> {
            map.computeIfAbsent(color.getBrandName(), i -> new ArrayList<>());
            map.get(color.getBrandName()).add(new SearchResponseDTO(color.getId(), color.getName(),true, color.getHexColor()));
        });
        return ResponseEntity.ok(map);
    }

    @PostMapping("/color")
    private ResponseEntity<Map<String, List<SearchResponseDTO>>> search(@RequestBody ColorSearchRequestDTO colorSearchRequestDTO) {
        Map<String, List<SearchResponseDTO>> map = new HashMap<>();
        lipstickColorSearchService.search(colorSearchRequestDTO.getHexColor())
                .forEach(color -> {
                    map.computeIfAbsent(color.getBrandName(), i -> new ArrayList<>());
                    map.get(color.getBrandName()).add(new SearchResponseDTO(color.getId(), color.getName(), true, color.getHexColor()));
        });
        return ResponseEntity.ok(map);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh() {
        lipstickSearchRepository.deleteAll();
        lipstickColorSearchRepository.deleteAll();

        List<LipstickSearch> lipsticks = new ArrayList<>();
        List<LipstickColorSearch> colors = new ArrayList<>();
        lipstickRepository.findAll()
                .forEach(lipstick -> {
                    String brandName = lipstick.getBrand().getName();
                    lipsticks.add(
                            new LipstickSearch(
                                    String.valueOf(lipstick.getId()),
                                    brandName,
                                    lipstick.getName()
                            )
                    );
                    lipstick.getColors()
                            .forEach(color ->
                                    colors.add(
                                            new LipstickColorSearch(
                                                    String.valueOf(color.getId()),
                                                    brandName,
                                                    color.getName(),
                                                    color.getHexColor()
                                            )
                                    )
                            );
                });
        lipstickSearchRepository.saveAll(lipsticks);
        lipstickColorSearchRepository.saveAll(colors);
        return ResponseEntity.ok().build();
    }
}
