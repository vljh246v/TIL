package demo.gettingstarted.persistence.jpa.entitygraph.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import demo.gettingstarted.persistence.jpa.entitygraph.entity.Comment;
import demo.gettingstarted.persistence.jpa.entitygraph.entity.Post;
import demo.gettingstarted.persistence.jpa.entitygraph.entity.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void save() {
        final User user1 = new User("name1", "email1@email.com");
        final User user2 = new User("name2", "email2@email.com");

        userRepository.save(user1);
        userRepository.save(user2);

        final Post post1 = new Post("subject1");
        post1.setUser(user1);
        final Post post2 = new Post("subject2");
        post2.setUser(user2);

        postRepository.save(post1);
        postRepository.save(post2);

        final Comment comment1 = new Comment("reply1");
        comment1.setPost(post1);
        final Comment comment2 = new Comment("reply2");
        comment2.setPost(post2);

        commentRepository.save(comment1);
        commentRepository.save(comment2);

        final List<Comment> comments = commentRepository.findAll();
        comments.forEach(it -> {
            System.out.println(it);
            System.out.println(it.getPost());
        });

    }
}