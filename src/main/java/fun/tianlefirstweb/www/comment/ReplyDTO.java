package fun.tianlefirstweb.www.comment;

import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
public class ReplyDTO {

    private Integer id;
    private String createTime;
    private Integer userId;
    private String username;
    private String content;

    public ReplyDTO(Reply reply) {
        this.id = reply.getId();
        this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reply.getCreateTime());
        this.userId = reply.getUser().getId();
        this.username = reply.getUser().getUsername();
        this.content = reply.getContent();
    }
}
