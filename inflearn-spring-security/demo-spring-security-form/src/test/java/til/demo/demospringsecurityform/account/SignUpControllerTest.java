package til.demo.demospringsecurityform.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignUpControllerTest {

  @Autowired
  MockMvc mockMvc;


  @Test
  public void singUpForm() throws Exception {
    mockMvc.perform(get("/signup"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("_csrf")));
  }

  @Test
  public void processSignUp() throws Exception {
    mockMvc.perform(post("/signup")
        .param("username", "demo")
        .param("password", "123")
        .with(csrf())
    ).andDo(print())
        .andExpect(status().is3xxRedirection());
  }
}