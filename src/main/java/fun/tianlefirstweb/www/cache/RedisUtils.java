package fun.tianlefirstweb.www.cache;

import fun.tianlefirstweb.www.product.brand.Brand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisUtils {

    @Bean
    RedisTemplate<String, Brand> redisTemplate(RedisConnectionFactory rcf) {

        RedisTemplate<String, Brand> template = new RedisTemplate<>();
        template.setConnectionFactory(rcf);
        template.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setEnableTransactionSupport(true);
//        template.afterPropertiesSet();

        return template;
    }
}
