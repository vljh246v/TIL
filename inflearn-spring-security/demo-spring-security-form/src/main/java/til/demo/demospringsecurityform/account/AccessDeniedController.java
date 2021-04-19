package til.demo.demospringsecurityform.account;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

  @GetMapping("/access-denied")
  public String accessDenied(Principal principal, Model model){
    model.addAttribute("name", principal.getName());
    return "access-denied";
  }

}
