package fun.tianlefirstweb.www.search.lipstickColor;

import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/colors")
public class LipstickColorSearchController {

    private final LipstickColorRepository lipstickColorRepository;
    private final LipstickColorSearchService lipstickColorSearchService;

    public LipstickColorSearchController(LipstickColorRepository lipstickColorRepository, LipstickColorSearchService lipstickColorSearchService) {
        this.lipstickColorRepository = lipstickColorRepository;
        this.lipstickColorSearchService = lipstickColorSearchService;
    }


    private ResponseEntity<?> refresh(){
        //TODO
        lipstickColorRepository.findAll();
        return ResponseEntity.ok().build();
    }
}
