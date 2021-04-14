package til.demo.demospringsecurityform.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

  @GetMapping("/login")
  public String loginForm(){
    return "login";
  }

  @GetMapping("/logout")
  public String logoutForm(){
    return "logout";
  }
}
