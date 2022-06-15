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

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receivePublicMessage(@Payload Message message){
        if(message.getStatus().equals(JOIN)) {
            message.setData(String.valueOf(simpUserRegistry.getUserCount()));
        }
        return message;
    }

    @MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message){
        messagingTemplate.convertAndSendToUser(message.getReceiver(),"/private", message);
        return message;
    }
}
