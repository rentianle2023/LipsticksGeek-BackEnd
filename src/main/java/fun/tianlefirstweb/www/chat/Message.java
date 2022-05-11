package fun.tianlefirstweb.www.chat;

import lombok.Data;

@Data
public class Message {

    private String data;
    private String sender;
    private String receiver;
    private Status status;
}
