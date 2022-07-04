package fun.tianlefirstweb.www.product.brand;

import fun.tianlefirstweb.www.cache.BrandRedisTemplate;
import fun.tianlefirstweb.www.exception.EntityAlreadyExistException;
import fun.tianlefirstweb.www.exception.EntityNotExistException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandRedisTemplate brandRedisTemplate;

    public BrandService(BrandRepository brandRepository, BrandRedisTemplate brandRedisTemplate) {
        this.brandRepository = brandRepository;
        this.brandRedisTemplate = brandRedisTemplate;
    }

    /**
     * 保存/更新品牌，双删缓存
     */
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
        if(brandRedisTemplate.hasKey()) {
            return brandRedisTemplate.findAll();
        }

        List<Brand> brands = brandRepository.findAll();
        brandRedisTemplate.saveAll(brands);
        return brands;
    }

    public Brand findById(Integer brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotExistException("品牌不存在"));
    }
}
