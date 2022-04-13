package fun.tianlefirstweb.www.product.lipstickColor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LipstickColorService {

    private final LipstickColorRepository lipstickColorRepository;

    public LipstickColorService(LipstickColorRepository lipstickColorRepository) {
        this.lipstickColorRepository = lipstickColorRepository;
    }

    public List<LipstickColor> getByLipstickId(Integer lipstickId){
        return lipstickColorRepository.findLipstickColorByLipstickId(lipstickId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("id为%d的口红不存在",lipstickId)));
    }

    public LipstickColor getById(Integer lipstickColorId){
        return lipstickColorRepository.findById(lipstickColorId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("id为%d的口红色号不存在",lipstickColorId)));
    }

    public void updateLipstickColor(Integer lipstickColorId, String newName, String newColor){
        LipstickColor color = getById(lipstickColorId);
        if(newName != null) color.setName(newName);
        if(newColor != null) color.setHexColor(newColor);
        lipstickColorRepository.save(color);
    }
}
