package fun.tianlefirstweb.www.crawler;

import fun.tianlefirstweb.www.crawler.fetcher.FetcherFactory;
import fun.tianlefirstweb.www.product.brand.Brand;
import fun.tianlefirstweb.www.product.brand.BrandService;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.lipstick.LipstickService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawlerService {

    private final FetcherFactory fetcherFactory;
    private final LipstickService lipstickService;
    private final BrandService brandService;

    public CrawlerService(FetcherFactory fetcherFactory, LipstickService lipstickService, BrandService brandService) {
        this.fetcherFactory = fetcherFactory;
        this.lipstickService = lipstickService;
        this.brandService = brandService;
    }

    public void fetchAndSaveLipsticks(String brandName){
        List<Lipstick> lipsticks = fetcherFactory.getFetcher(brandName).fetch();
        bindBrandWithLipsticks(lipsticks,brandName);
        lipstickService.saveAll(lipsticks);
    }

    private void bindBrandWithLipsticks(List<Lipstick> lipsticks, String brandName){
        Brand brand = brandService.getBrandByName(brandName);
        lipsticks.forEach(lipstick -> lipstick.setBrand(brand));
    }
}
