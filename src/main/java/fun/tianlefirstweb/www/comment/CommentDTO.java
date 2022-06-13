package fun.tianlefirstweb.www.comment;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fun.tianlefirstweb.www.user.ApplicationUser;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class CommentDTO {

    private Integer id;
    private String createTime;
    private String content;

    private Integer userId;
    private String username;

    private List<ReplyDTO> replies;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(comment.getCreateTime());
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.username = comment.getUser().getUsername();
    }
}
