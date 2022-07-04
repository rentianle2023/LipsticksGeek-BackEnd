package fun.tianlefirstweb.www.cache;

import fun.tianlefirstweb.www.product.brand.Brand;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BrandRedisTemplate {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String BRAND_KEY = "brand";
    private final HashOperations<String,Integer,Brand> hashOperations;

    public BrandRedisTemplate(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(Brand brand) {
        hashOperations.put(BRAND_KEY,brand.getId(),brand);
    }

    public List<Brand> findAll(){
        return hashOperations.values(BRAND_KEY);
    }

    public void deleteAll(){
        redisTemplate.delete(BRAND_KEY);
    }

    public void saveAll(List<Brand> brands){
        Map<Integer, Brand> brandsMap = brands.stream().collect(
                Collectors.toMap(Brand::getId, brand -> brand));
        hashOperations.putAll(BRAND_KEY,brandsMap);
    }

    public Boolean hasKey() {
        return redisTemplate.hasKey(BRAND_KEY);
    }
}
