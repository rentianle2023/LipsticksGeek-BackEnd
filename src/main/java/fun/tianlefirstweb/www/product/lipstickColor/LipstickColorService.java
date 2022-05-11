package fun.tianlefirstweb.www.product.lipstickColor;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import org.springframework.stereotype.Service;

@Service
public class LipstickColorService {

    private final LipstickColorRepository lipstickColorRepository;

    public LipstickColorService(LipstickColorRepository lipstickColorRepository) {
        this.lipstickColorRepository = lipstickColorRepository;
    }

    public LipstickColor findById(Integer lipstickColorId){
        return lipstickColorRepository.findById(lipstickColorId)
                .orElseThrow(() -> new EntityNotExistException(String.format("id为%d的口红色号不存在",lipstickColorId)));
    }

    public void updateLipstickColor(Integer lipstickColorId, String newName, String newColor){
        LipstickColor color = findById(lipstickColorId);
        if(newName != null) color.setName(newName);
        if(newColor != null) color.setHexColor(newColor);
        lipstickColorRepository.save(color);
    }

    public void save(LipstickColor lipstickColor){
        lipstickColorRepository.save(lipstickColor);
    }
}
