package fun.tianlefirstweb.www.chat;

import fun.tianlefirstweb.www.cache.BrandRedisTemplate;
import fun.tianlefirstweb.www.cache.ChatRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Controller
public class ChatController{

    private final SimpMessagingTemplate messagingTemplate;
    private final SimpUserRegistry simpUserRegistry;
    private final ChatRedisTemplate chatRedisTemplate;
    private final String MESSAGES_KEY = "messages";

    public ChatController(SimpMessagingTemplate messagingTemplate,
                          SimpUserRegistry simpUserRegistry,
                          ChatRedisTemplate chatRedisTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
        this.chatRedisTemplate = chatRedisTemplate;
    }

    /**
     * @param message 消息
     * 广播到所有订阅/chatroom/public的用户
     */
    @MessageMapping("/message")
    public void receivePublicMessage(@Payload Message message){

        switch (message.getStatus()) {
            case JOIN: message.setData(String.valueOf(simpUserRegistry.getUserCount())); break;
            case LEAVE: message.setData(String.valueOf(simpUserRegistry.getUserCount() - 1)); break;
            case MESSAGE: chatRedisTemplate.save(message); break;
        }

        messagingTemplate.convertAndSend("/chatroom/public",message);
    }

    /**
     * 发送信息到私人频道
     * @param message 包含receiver信息的消息
     * @return 转发给订阅/user/{username}/private的用户
     */
    @MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message){
        messagingTemplate.convertAndSendToUser(message.getReceiver(),"/private", message);
        return message;
    }

    @GetMapping("/messages")
    @ResponseBody
    public ResponseEntity<List<Message>> getAllChatMessages(){
        List<Message> messages = chatRedisTemplate.findAll();
        return ResponseEntity.ok(messages);
    }
}
