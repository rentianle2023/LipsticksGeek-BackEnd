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

    /**
     * 保存口红，新保存的口红默认为active
     */
    public void save(Lipstick lipstick){
        lipstick.setActive(true);
        lipstickRepository.save(lipstick);
    }

    /**
     * 根据品牌id获取active的口红以及其所有色号
     */
    public List<Lipstick> findByBrandId(Integer brandId){
        if(!brandRepository.existsById(brandId)){
            throw new EntityNotExistException(String.format("id为%d的品牌不存在",brandId));
        }
        return lipstickRepository.findLipsticksWithColorsByBrandIdAndActive(brandId,true);
    }

    /**
     * 根据口红id获取口红以及其所有色号
     */
    public Lipstick findById(Integer lipstickId){
        return lipstickRepository.findLipstickWithColorsById(lipstickId)
                .orElseThrow(() -> new EntityNotExistException(String.format("id为%d的口红不存在",lipstickId)));
    }

    /**
     * 根据口红名称获取口红以及其所有色号
     */
    public Lipstick findByName(String lipstickName){
        return lipstickRepository.findLipstickWithColorsByName(lipstickName)
                .orElseThrow(() -> new EntityNotExistException(String.format("名称为%s的口红不存在",lipstickName)));
    }

    /**
     * 更新口红信息
     */
    public void update(Lipstick lipstick){
        lipstickRepository.update(lipstick.getId(),lipstick.getName(),lipstick.getPrice(),lipstick.getImageUrl());
    }

    /**
     * 根据口红名称判断其是否存在
     */
    public boolean existsByName(String lipstickName) {
        return lipstickRepository.existsByName(lipstickName);
    }

    /**
     * 分页获取指定page&size的所有口红以及其所有色号
     */
    public Page<Lipstick> findLipsticksWithPagination(int page, int size){
        return lipstickRepository.findLipstickWithColors(
                PageRequest.of(page, size)
        );
    }

    /**
     * 获取所有active的口红以及其所有色号
     * @return
     */
    public List<Lipstick> findAllActive() {
        return lipstickRepository.findAllByActive(true);
    }

    public Lipstick findLipstickByColorId(Integer colorId) {
        return lipstickRepository.findLipstickByColorId(colorId)
                .orElseThrow(() -> new EntityNotExistException("无法根据该口红色号获取任何口红信息"));
    }
}
