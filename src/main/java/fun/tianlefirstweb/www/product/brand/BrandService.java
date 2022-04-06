package fun.tianlefirstweb.www.product.brand;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public void save(Brand brand){
        if(brandRepository.existsBrandByName(brand.getName())){
            throw new IllegalArgumentException(String.format("%s品牌已存在",brand.getName()));
        }
        brandRepository.save(brand);
    }

    public Brand getBrandByName(String brandName){
        return brandRepository.findBrandByName(brandName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%s品牌不存在",brandName)));
    }

    public Brand getBrand(Integer brandId){
        return brandRepository.findById(brandId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("id为%d的品牌不存在",brandId)));
    }

    public List<Brand> getBrands(){
        return brandRepository.findAll();
    }
}
