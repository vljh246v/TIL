package demo.gettingstarted.persistence.jpa.entitygraph.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.gettingstarted.persistence.jpa.entitygraph.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
