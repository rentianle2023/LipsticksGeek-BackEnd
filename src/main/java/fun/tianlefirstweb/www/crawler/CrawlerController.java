package fun.tianlefirstweb.www.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    private final CrawlerService crawlerService;

    @Autowired
    public CrawlerController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @PostMapping("/{brandName}")
    public ResponseEntity<Integer> fetchAndUpdate(@PathVariable String brandName){
        Integer newColorsCount = crawlerService.fetchAndUpdate(brandName);
        return ResponseEntity.ok(newColorsCount);
    }
}
