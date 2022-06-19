package fun.tianlefirstweb.www.product.lipstickColor;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void updateLipstickColor(LipstickColor lipstickColor){
        LipstickColor color = lipstickColorRepository.findById(lipstickColor.getId())
                .orElseThrow(() -> new EntityNotExistException("色号不存在"));
        if(lipstickColor.getName() != null) color.setName(lipstickColor.getName());
        if(lipstickColor.getHexColor() != null) color.setHexColor(lipstickColor.getHexColor());
        if(lipstickColor.getTags() != null) color.setTags(lipstickColor.getTags());
        lipstickColorRepository.save(color);
    }

    public void save(LipstickColor lipstickColor){
        lipstickColorRepository.save(lipstickColor);
    }
}
