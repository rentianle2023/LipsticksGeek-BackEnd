package fun.tianlefirstweb.www.cache;

import fun.tianlefirstweb.www.product.lipstickColor.LipstickColorDetailsDTO;
import fun.tianlefirstweb.www.product.tag.Tag;
import fun.tianlefirstweb.www.product.tag.TagTitle;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagRedisTemplate {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String TAG_KEY_PREFIX = "tag_";
    private final ListOperations<String, LipstickColorDetailsDTO> listOperations;

    public TagRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = redisTemplate.opsForList();
    }

    public void save(TagTitle tagTitle, List<LipstickColorDetailsDTO> brandColorMap) {
        listOperations.rightPushAll(TAG_KEY_PREFIX + tagTitle, brandColorMap);
    }

    public void deleteAll() {
        redisTemplate.delete(
                Arrays.stream(TagTitle.values())
                        .map(title -> TAG_KEY_PREFIX + title)
                        .collect(Collectors.toList())
        );
    }

    public void delete(TagTitle tagTitle) {
        redisTemplate.delete(TAG_KEY_PREFIX + tagTitle);
    }

    public Boolean hasKey(TagTitle tagTitle) {
        return redisTemplate.hasKey(TAG_KEY_PREFIX + tagTitle);
    }

    public List<LipstickColorDetailsDTO> findAll(TagTitle tagTitle) {
        return listOperations.range(TAG_KEY_PREFIX + tagTitle,0,-1);
    }
}
