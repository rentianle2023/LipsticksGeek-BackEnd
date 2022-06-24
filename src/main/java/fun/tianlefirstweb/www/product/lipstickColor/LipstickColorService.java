package fun.tianlefirstweb.www.product.lipstickColor;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.product.lipstick.Lipstick;
import fun.tianlefirstweb.www.product.tag.TagTitle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LipstickColorService {

    private final LipstickColorRepository lipstickColorRepository;

    public LipstickColorService(LipstickColorRepository lipstickColorRepository) {
        this.lipstickColorRepository = lipstickColorRepository;
    }

    /**
     * 根据色号id获取色号
     */
    public LipstickColor findById(Integer lipstickColorId){
        return lipstickColorRepository.findById(lipstickColorId)
                .orElseThrow(() -> new EntityNotExistException(String.format("id为%d的口红色号不存在",lipstickColorId)));
    }

    /**
     * 更新色号
     */
    public void updateLipstickColor(LipstickColor lipstickColor){
        if(!lipstickColorRepository.existsById(lipstickColor.getId()))
                throw new EntityNotExistException("色号不存在");
        lipstickColorRepository.save(lipstickColor);
    }

    /**
     * 保存色号
     */
    public void save(LipstickColor lipstickColor){
        lipstickColorRepository.save(lipstickColor);
    }

    /**
     * 根据色号id获得色号详细信息
     */
    public LipstickColorDetailsDTO findColorDetailsById(Integer colorId) {
        LipstickColor color = findById(colorId);
        return new LipstickColorDetailsDTO(color);
    }

    /**
     * 根据标签获取所有符合该标签的口红
     */
    public List<LipstickColor> findLipstickColorsByTagTitle(TagTitle tagTitle) {
        return lipstickColorRepository.findLipstickColorsByTag(tagTitle.name());
    }
}
