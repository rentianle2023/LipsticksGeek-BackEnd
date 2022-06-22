package fun.tianlefirstweb.www.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.messaging.MessageHeaders;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String data;
    private String sender;
    private String receiver;
    private Status status;
}
