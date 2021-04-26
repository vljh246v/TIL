package til.demo.demospringsecurityform.book;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface BookRepository extends JpaRepository<Book, Integer> {

  @Query("select b from Book b where b.author.id = ?#{principal.account.id}")
  List<Book> findCurrentUserBooks();
}
