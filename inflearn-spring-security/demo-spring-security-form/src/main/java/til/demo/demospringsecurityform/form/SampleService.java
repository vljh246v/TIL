package til.demo.demospringsecurityform.form;

import org.springframework.stereotype.Service;
import til.demo.demospringsecurityform.account.Account;
import til.demo.demospringsecurityform.account.AccountContext;

@Service
public class SampleService {

  public void dashboard() {
    Account account = AccountContext.getAccount();
    System.out.println("-------------------");
    System.out.println(account.getUsername());
  }
}
