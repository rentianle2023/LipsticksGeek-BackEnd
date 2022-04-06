package fun.tianlefirstweb.www.product.lipstick;

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

    public void saveAll(List<Lipstick> lipsticks){
        lipstickRepository.saveAll(lipsticks);
    }

    public List<Lipstick> getLipsticks(Integer brandId){
        return lipstickRepository.findLipsticksByBrandId(brandId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("id为%d的品牌不存在",brandId)));
    }

    public Lipstick getLipstick(Integer lipstickId){
        return lipstickRepository.findById(lipstickId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("id为%d的口红不存在",lipstickId)));
    }

    public void updateLipstick(Integer lipstickId, String newName, String newPrice, String newImageUrl){
        Lipstick lipstick = getLipstick(lipstickId);
        if(newName != null) lipstick.setName(newName);
        if(newPrice != null) lipstick.setPrice(newPrice);
        if(newImageUrl != null) lipstick.setImageUrl(newImageUrl);
        lipstickRepository.save(lipstick);
    }
}
