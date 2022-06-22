package fun.tianlefirstweb.www.cache;

import fun.tianlefirstweb.www.product.brand.Brand;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BrandCache {

    private final RedisTemplate<String,Brand> redisTemplate;
    private final String BRAND_HASH_KEY = "BRAND";
    private final HashOperations<String,Integer,Brand> hashOperations;

    public BrandCache(RedisTemplate<String,Brand> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(Brand brand) {
        hashOperations.put(BRAND_HASH_KEY,brand.getId(),brand);
    }

    public Brand findById(Integer brandId) {
        return (Brand)redisTemplate.opsForHash().get(BRAND_HASH_KEY,brandId);
    }

    public List<Brand> findAll(){
        return hashOperations.values(BRAND_HASH_KEY);
    }

    public void update(Brand brand) {
        save(brand);
    }

    public void delete(Integer id) {
        hashOperations.delete(BRAND_HASH_KEY,id);
    }

    public void deleteAll(){
        redisTemplate.delete(BRAND_HASH_KEY);
    }

    public void saveAll(List<Brand> brands){
        Map<Integer, Brand> brandsMap = brands.stream().collect(
                Collectors.toMap(Brand::getId, brand -> brand));
        hashOperations.putAll(BRAND_HASH_KEY,brandsMap);
    }
}
