package fun.tianlefirstweb.www.crawler.fetcher;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.product.brand.Brand;
import fun.tianlefirstweb.www.product.brand.BrandRepository;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;

import java.util.List;

public abstract class LipstickFetcher implements Fetcher<Lipstick>{

    private final BrandRepository brandRepository;

    protected LipstickFetcher(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Lipstick> fetch() {
        List<Lipstick> lipsticks = fetchLipsticks();
        afterFetch(lipsticks);
        return lipsticks;
    }

    protected abstract List<Lipstick> fetchLipsticks();

    protected abstract String getBrandName();

    private void afterFetch(List<Lipstick> lipsticks){
        Brand brand = getBrand();
        lipsticks
                .forEach(lipstick -> {
                    lipstick.getColors().forEach(color -> color.setLipstick(lipstick));
                    lipstick.setBrand(brand);
                });
    }


    private Brand getBrand() {
        return brandRepository.findBrandByName(getBrandName())
                .orElseThrow(() -> new EntityNotExistException(String.format("Brand %s does not exist",getBrandName())));
    }
}
