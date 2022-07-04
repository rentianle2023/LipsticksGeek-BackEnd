package fun.tianlefirstweb.www.cache;

import fun.tianlefirstweb.www.chat.Message;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class ChatRedisTemplate {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ListOperations<String,Message> listOperations;
    private final String MESSAGES_KEY = "messages";
    private final Integer EXPIRE_HOUR = 1;

    public ChatRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOperations = redisTemplate.opsForList();
    }

    public void save(Message message) {
        listOperations.rightPush(MESSAGES_KEY,message);
        updateExpire();
    }

    private void updateExpire() {
        redisTemplate.expireAt(
                MESSAGES_KEY,
                Instant.now().plus(Duration.ofHours(EXPIRE_HOUR))
        );
    }

    public List<Message> findAll(){
        return listOperations.range(MESSAGES_KEY,0,-1);
    }
}
