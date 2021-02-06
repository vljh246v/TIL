package til.demo.demospringsecurityform.form;

import java.security.Principal;
import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

  @GetMapping("/")
  public String index(Model model, Principal principal){
    if(Objects.isNull(principal)){
      model.addAttribute("message", "Hello Spring Security");
    } else {
      model.addAttribute("message", "Hello " + principal.getName());
    }
    return "index";
  }

  @GetMapping("/info")
  public String info(Model model){
    model.addAttribute("message", "Info");
    return "info";
  }

  @GetMapping("/dashboard")
  public String dashboard(Model model, Principal principal){
    model.addAttribute("message", "Hello " + principal.getName());
    return "dashboard";
  }

  @GetMapping("/admin")
  public String admin(Model model, Principal principal){
    model.addAttribute("message", "Hello Admin " + principal.getName());
    return "admin";
  }
}
