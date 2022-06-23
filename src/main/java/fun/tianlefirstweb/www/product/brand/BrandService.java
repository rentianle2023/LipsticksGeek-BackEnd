package fun.tianlefirstweb.www.product.brand;

import fun.tianlefirstweb.www.cache.BrandRedisTemplate;
import fun.tianlefirstweb.www.exception.EntityAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandRedisTemplate brandRedisTemplate;

    public BrandService(BrandRepository brandRepository, BrandRedisTemplate brandRedisTemplate) {
        this.brandRepository = brandRepository;
        this.brandRedisTemplate = brandRedisTemplate;
    }

    public void save(Brand brand){
        if(brandRepository.existsBrandByName(brand.getName())){
            throw new EntityAlreadyExistException(String.format("%s品牌已存在",brand.getName()));
        }
        brandRedisTemplate.deleteAll();
        brandRepository.save(brand);
        brandRedisTemplate.deleteAll();
    }

    /**
     * 获取所有品牌信息，缓存优先
     */
    public List<Brand> findBrands(){
        List<Brand> cachedBrands = brandRedisTemplate.findAll();
        if(!cachedBrands.isEmpty()) {
            return cachedBrands;
        }

        List<Brand> brands = brandRepository.findAll();
        brandRedisTemplate.saveAll(brands);
        return brands;
    }
}
