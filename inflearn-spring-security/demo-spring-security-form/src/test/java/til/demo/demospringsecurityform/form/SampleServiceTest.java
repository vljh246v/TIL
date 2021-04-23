package til.demo.demospringsecurityform.form;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import til.demo.demospringsecurityform.account.Account;
import til.demo.demospringsecurityform.account.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {

  @Autowired
  SampleService sampleService;

  @Autowired
  AccountService accountService;

  @Autowired
  AuthenticationManager authenticationManager;


  @Test
  public void dashboard(){
    Account account = new Account();
    account.setRole("ADMIN");
    account.setUsername("demo");
    account.setPassword("123");
    accountService.createNew(account);

    UserDetails userDetails = accountService.loadUserByUsername("demo");

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
    Authentication authenticate = authenticationManager.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    sampleService.dashboard();
  }
}