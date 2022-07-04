package fun.tianlefirstweb.www.product.lipstickColor;

import fun.tianlefirstweb.www.cache.TagRedisTemplate;
import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.product.tag.Tag;
import fun.tianlefirstweb.www.product.tag.TagTitle;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LipstickColorService {

    private final LipstickColorRepository lipstickColorRepository;
    private final TagRedisTemplate tagRedisTemplate;

    public LipstickColorService(LipstickColorRepository lipstickColorRepository, TagRedisTemplate tagRedisTemplate) {
        this.lipstickColorRepository = lipstickColorRepository;
        this.tagRedisTemplate = tagRedisTemplate;
    }

    /**
     * 根据色号id获取色号
     */
    public LipstickColor findById(Integer lipstickColorId){
        return lipstickColorRepository.findById(lipstickColorId)
                .orElseThrow(() -> new EntityNotExistException(String.format("id为%d的口红色号不存在",lipstickColorId)));
    }

    /**
     * 更新色号，对于影响到的缓存tag进行删除
     */
    public void updateLipstickColor(LipstickColor newColor){
        LipstickColor persistColor = lipstickColorRepository.findById(newColor.getId())
                .orElseThrow(() -> new EntityNotExistException("色号不存在"));
        persistColor.setHexColor(newColor.getHexColor());
        persistColor.setName(newColor.getName());

        List<Tag> updatedTags = newColor.getTags();
        List<Tag> oldTags = persistColor.getTags();
        updatedTags.stream()
                .filter(tag -> !oldTags.contains(tag))
                .forEach(tag -> tagRedisTemplate.delete(tag.getTitle()));
        oldTags.stream()
                .filter(tag -> !updatedTags.contains(tag))
                .forEach(tag -> tagRedisTemplate.delete(tag.getTitle()));

        persistColor.setTags(newColor.getTags());

        lipstickColorRepository.save(persistColor);
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
        //根据色号id获取detail，key = 'brand'brandId_colorId


        LipstickColor color = findById(colorId);
        return new LipstickColorDetailsDTO(color);
    }

    /**
     * 根据标签获取所有符合该标签的口红
     */
    public List<LipstickColorDetailsDTO> findLipstickColorDetailsByTagTitle(TagTitle tagTitle) {
        //if tag has cache
        //1. data structure: tag -> HashMap<String,List<LipstickColorDetailsDTO>>
        //2. update strategy: if brand update, delete all the tags key, if color update, check tags and remove that tag

        if (tagRedisTemplate.hasKey(tagTitle) ) {
            return tagRedisTemplate.findAll(tagTitle);
        }
        List<LipstickColor> colors = lipstickColorRepository.findLipstickColorsByTag(tagTitle.name());

        List<LipstickColorDetailsDTO> colorDetailsList = colors
                .stream()
                .map(LipstickColorDetailsDTO::new)
                .collect(Collectors.toList());
        tagRedisTemplate.save(tagTitle,colorDetailsList);
        return colorDetailsList;
    }
}
