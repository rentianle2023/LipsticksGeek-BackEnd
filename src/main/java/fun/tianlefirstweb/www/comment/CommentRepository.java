package fun.tianlefirstweb.www.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    @Query("SELECT DISTINCT c FROM Comment c LEFT JOIN FETCH c.replies r LEFT JOIN FETCH c.user u ORDER BY c.createTime")
    List<Comment> findAllCommentsWithReplies();
}
