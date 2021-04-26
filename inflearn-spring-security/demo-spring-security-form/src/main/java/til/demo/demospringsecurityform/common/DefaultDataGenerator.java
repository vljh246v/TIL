package til.demo.demospringsecurityform.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import til.demo.demospringsecurityform.account.Account;
import til.demo.demospringsecurityform.account.AccountService;
import til.demo.demospringsecurityform.book.Book;
import til.demo.demospringsecurityform.book.BookRepository;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

  @Autowired
  AccountService accountService;

  @Autowired
  BookRepository bookRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    Account demo = createUser("demo");
    Account etc = createUser("etc");

    createBook("spring", demo);
    createBook("jpa", etc);
  }

  private void createBook(String bookName, Account authorName) {
    Book book = new Book();
    book.setTitle(bookName);
    book.setAuthor(authorName);
    bookRepository.save(book);
  }

  private Account createUser(String name) {
    Account account = new Account();
    account.setUsername(name);
    account.setPassword("123");
    account.setRole("USER");

    return accountService.createNew(account);
  }
}
