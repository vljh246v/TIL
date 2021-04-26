package til.demo.demospringsecurityform.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import til.demo.demospringsecurityform.account.Account;

@Getter
@Setter
@ToString
@Entity
public class Book {

  @Id @GeneratedValue
  private Integer id;

  private String title;

  @ManyToOne
  private Account author;

}
