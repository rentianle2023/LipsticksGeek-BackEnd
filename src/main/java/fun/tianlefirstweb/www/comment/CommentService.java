package fun.tianlefirstweb.www.comment;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment){
        Timestamp timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toLocalDateTime());
        System.out.println(timestamp.toString());
        comment.setCreateTime(timestamp);
        Comment save = commentRepository.save(comment);
        System.out.println(save.getCreateTime().toString());
        return save;
    }

    public List<Comment> findAllComments(){
        return commentRepository.findAllCommentsWithReplies();
    }

    public Comment findCommentById(Integer commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotExistException(String.format("id为%d的评论不存在",commentId)));
    }
}
