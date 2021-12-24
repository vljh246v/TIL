package demo.gettingstarted.persistence.jpa.entitygraph.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.gettingstarted.persistence.jpa.entitygraph.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(value = "post-entity-graph")
    Optional<Post> findById(Long aLong);
}
