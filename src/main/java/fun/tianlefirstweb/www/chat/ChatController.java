package fun.tianlefirstweb.www.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import static fun.tianlefirstweb.www.chat.Status.JOIN;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SimpUserRegistry simpUserRegistry;

    public ChatController(SimpMessagingTemplate messagingTemplate,
                          SimpUserRegistry simpUserRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }

    /**
     * 发送信息到公共频道
     * @param message 消息
     * @return 广播到所有订阅/chatroom/public的用户
     */
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receivePublicMessage(@Payload Message message){
        if(message.getStatus().equals(JOIN)) {
            message.setData(String.valueOf(simpUserRegistry.getUserCount()));
        }
        return message;
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
}
