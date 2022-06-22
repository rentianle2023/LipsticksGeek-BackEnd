package fun.tianlefirstweb.www.product.brand;

import fun.tianlefirstweb.www.cache.BrandCache;
import fun.tianlefirstweb.www.exception.EntityAlreadyExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandCache brandCache;

    public BrandService(BrandRepository brandRepository, BrandCache brandCache) {
        this.brandRepository = brandRepository;
        this.brandCache = brandCache;
    }

    //DOUBLE DELETE???
    public void save(Brand brand){
        new Thread(brandCache::deleteAll).start();
        if(brandRepository.existsBrandByName(brand.getName())){
            throw new EntityAlreadyExistException(String.format("%s品牌已存在",brand.getName()));
        }
        brandRepository.save(brand);
    }

    //CACHE before DB ?
    public List<Brand> findBrands(){
        List<Brand> cachedBrands = brandCache.findAll();
        if(!cachedBrands.isEmpty()) {
            return cachedBrands;
        }

        List<Brand> brands = brandRepository.findAll();
        brandCache.saveAll(brands);
        return brands;
    }

    //TODO:没用
    public Page<Brand> findBrandsWithPagination(int page, int size){
        return brandRepository.findAll(
                PageRequest.of(page, size)
        );
    }
}
