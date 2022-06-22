package fun.tianlefirstweb.www.comment;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentService commentService;

    public ReplyService(ReplyRepository replyRepository, CommentService commentService) {
        this.replyRepository = replyRepository;
        this.commentService = commentService;
    }

    public Reply addReply(Integer commentId,Reply reply){
        Comment comment = commentService.findCommentById(commentId);
        reply.setComment(comment);
        reply.setCreateTime(Timestamp.valueOf(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toLocalDateTime()));
        return replyRepository.save(reply);
    }
}
