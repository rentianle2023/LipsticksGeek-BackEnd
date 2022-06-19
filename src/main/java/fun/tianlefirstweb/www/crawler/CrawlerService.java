package fun.tianlefirstweb.www.crawler;

import fun.tianlefirstweb.www.crawler.fetcher.FetcherFactory;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstick.LipstickService;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColor;
import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CrawlerService {

    private final FetcherFactory fetcherFactory;
    private final LipstickService lipstickService;
    private final LipstickColorService lipstickColorService;

    public CrawlerService(FetcherFactory fetcherFactory,
                          LipstickService lipstickService,
                          LipstickColorService lipstickColorService) {
        this.fetcherFactory = fetcherFactory;
        this.lipstickService = lipstickService;
        this.lipstickColorService = lipstickColorService;
    }

    public Integer fetchAndUpdate(String brandName){
        List<Lipstick> lipsticks = fetcherFactory
                .getFetcher(brandName)
                .fetch();

        AtomicInteger count = new AtomicInteger();
        lipsticks.forEach(lipstick -> {
            if(!lipstickService.existsByName(lipstick.getName())) {
                lipstickService.save(lipstick);
                count.addAndGet(lipstick.getColors().size());
            }
            else{
                Lipstick persistLipstick = lipstickService.findByName(lipstick.getName());
                List<LipstickColor> colors = persistLipstick.getColors();
                lipstick.getColors().forEach(color -> {
                    if(!colors.contains(color)) {
                        color.setLipstick(persistLipstick);
                        lipstickColorService.save(color);
                        count.incrementAndGet();
                    }
                });
            }
        });

        return count.get();
    }
}
