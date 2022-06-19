package fun.tianlefirstweb.www.chat;

import fun.tianlefirstweb.www.security.jwt.JwtConfig;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtConfig jwtConfig;

    public AuthChannelInterceptorAdapter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            String token = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
            Authentication authentication = jwtConfig.verifyToken(token);
            accessor.setUser(authentication);
        }
        return message;
    }
}
