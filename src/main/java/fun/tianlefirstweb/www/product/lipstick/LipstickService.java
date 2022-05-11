package fun.tianlefirstweb.www.product.lipstick;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LipstickService {

    private final LipstickRepository lipstickRepository;

    public LipstickService(LipstickRepository lipstickRepository) {
        this.lipstickRepository = lipstickRepository;
    }

    public void save(Lipstick lipstick){
        lipstickRepository.save(lipstick);
    }

    public List<Lipstick> getByBrandId(Integer brandId){
        if(!lipstickRepository.existsById(brandId)){
            throw new EntityNotExistException(String.format("id为%d的品牌不存在",brandId));
        }
        return lipstickRepository.findLipsticksByBrandId(brandId);
    }

    public Lipstick getById(Integer lipstickId){
        return lipstickRepository.findById(lipstickId)
                .orElseThrow(() -> new EntityNotExistException(String.format("id为%d的口红不存在",lipstickId)));
    }

    public void update(Integer lipstickId, String newName, String newPrice, String newImageUrl){
        Lipstick lipstick = getById(lipstickId);
        if(newName != null) lipstick.setName(newName);
        if(newPrice != null) lipstick.setPrice(newPrice);
        if(newImageUrl != null) lipstick.setImageUrl(newImageUrl);
        lipstickRepository.save(lipstick);
    }

    public Lipstick findByName(String lipstickName){
        return lipstickRepository.findByName(lipstickName)
                .orElseThrow(() -> new EntityNotExistException(String.format("名称为%s的口红不存在",lipstickName)));
    }

    public boolean existsByName(String lipstickName){
        return lipstickRepository.existsByName(lipstickName);
    }

    public Iterable<Lipstick> findAll() {
        return lipstickRepository.findAll();
    }
}
