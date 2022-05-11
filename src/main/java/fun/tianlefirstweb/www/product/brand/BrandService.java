package fun.tianlefirstweb.www.product.brand;

import fun.tianlefirstweb.www.exception.EntityAlreadyExistException;
import fun.tianlefirstweb.www.exception.EntityNotExistException;
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
            throw new EntityAlreadyExistException(String.format("%s品牌已存在",brand.getName()));
        }
        brandRepository.save(brand);
    }

    public List<Brand> getBrands(){
        return brandRepository.findAll();
    }
}
