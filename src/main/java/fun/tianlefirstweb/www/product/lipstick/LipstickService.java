package fun.tianlefirstweb.www.product.lipstick;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.product.brand.BrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LipstickService {

    private final LipstickRepository lipstickRepository;
    private final BrandRepository brandRepository;

    public LipstickService(LipstickRepository lipstickRepository, BrandRepository brandRepository) {
        this.lipstickRepository = lipstickRepository;
        this.brandRepository = brandRepository;
    }

    public void save(Lipstick lipstick){
        lipstickRepository.save(lipstick);
    }

    public List<Lipstick> findByBrandId(Integer brandId){
        if(!brandRepository.existsById(brandId)){
            throw new EntityNotExistException(String.format("id为%d的品牌不存在",brandId));
        }
        return lipstickRepository.findLipsticksByBrandIdAndActive(brandId,true);
    }

    public Lipstick findById(Integer lipstickId){
        return lipstickRepository.findById(lipstickId)
                .orElseThrow(() -> new EntityNotExistException(String.format("id为%d的口红不存在",lipstickId)));
    }

    public void update(Lipstick newLipstick){
        Lipstick lipstick = findById(newLipstick.getId());
        if(newLipstick.getName() != null) lipstick.setName(newLipstick.getName());
        if(newLipstick.getPrice() != null) lipstick.setPrice(newLipstick.getPrice());
        if(newLipstick.getImageUrl() != null) lipstick.setImageUrl(newLipstick.getImageUrl());
        if(newLipstick.getActive() != null) lipstick.setActive(newLipstick.getActive());
        lipstickRepository.save(lipstick);
    }

    public Lipstick findByName(String lipstickName){
        return lipstickRepository.findByNameAndActive(lipstickName,true)
                .orElseThrow(() -> new EntityNotExistException(String.format("名称为%s的口红不存在",lipstickName)));
    }

    public boolean existsByName(String lipstickName){
        return lipstickRepository.existsByNameAndActive(lipstickName,true);
    }

    public List<Lipstick> findAll() {
        return lipstickRepository.findAllByActive(true);
    }

    public Page<Lipstick> findLipsticksWithPagination(int page, int size){
        return lipstickRepository.findAll(
                PageRequest.of(page, size)
        );
    }
}
