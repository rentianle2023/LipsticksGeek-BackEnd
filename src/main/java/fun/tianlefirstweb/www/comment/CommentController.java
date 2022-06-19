package fun.tianlefirstweb.www.comment;

import fun.tianlefirstweb.www.user.ApplicationUser;
import fun.tianlefirstweb.www.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final ReplyService replyService;
    private final UserService userService;

    public CommentController(CommentService commentService, ReplyService replyService, UserService userService) {
        this.commentService = commentService;
        this.replyService = replyService;
        this.userService = userService;
    }

    /**
     * 添加留言
     * @param authentication 获取当前用户信息
     * @param comment 留言
     */
    @PostMapping
    public ResponseEntity<CommentDTO> addComment(Authentication authentication,
                                        @Valid @RequestBody Comment comment){
        ApplicationUser user = userService.findByUsername(authentication.getName());
        comment.setUser(user);
        return ResponseEntity.ok(new CommentDTO(commentService.addComment(comment)));
    }

    /**
     * 获取所有留言
     */
    @GetMapping
    public ResponseEntity<List<CommentDTO>> findAllComments(){
        List<CommentDTO> comments = commentService.findAllComments()
                .stream()
                .map(comment -> {
                    List<ReplyDTO> replies = comment.getReplies().stream().map(ReplyDTO::new).collect(Collectors.toList());
                    CommentDTO commentDTO = new CommentDTO(comment);
                    commentDTO.setReplies(replies);
                    return commentDTO;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    /**
     * 给指定留言添加回复信息
     * @param commentId 留言id
     * @param reply 回复信息
     * @param authentication 回复用户信息
     */
    @PostMapping("/{commentId}/reply")
    public ResponseEntity<ReplyDTO> addReply(
            @PathVariable("commentId") Integer commentId,
            @Valid @RequestBody Reply reply,
            Authentication authentication){
        ApplicationUser user = userService.findByUsername(authentication.getName());
        reply.setUser(user);

        return ResponseEntity.ok(new ReplyDTO(replyService.addReply(commentId, reply)));
    }
}
