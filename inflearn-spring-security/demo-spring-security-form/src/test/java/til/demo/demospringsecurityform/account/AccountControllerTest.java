package til.demo.demospringsecurityform.account;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void index_anonymous() throws Exception {
    mockMvc.perform(get("/").with(anonymous()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @WithAnonymousUser
  public void index_anonymous_annotation() throws Exception {
    mockMvc.perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  // with(user("demo").roles("USER")) -> 이 유저가 이미 로그인한 상태이다 라고 가정하고 모킹한 상태
  @Test
  public void index_user() throws Exception {
    mockMvc.perform(get("/").with(user("demo").roles("USER")))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @WithUser
  public void index_user_annotation() throws Exception {
    mockMvc.perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void admin_user() throws Exception {
    mockMvc.perform(get("/admin").with(user("demo").roles("USER")))
        .andDo(print())
        .andExpect(status().isForbidden());
  }

  @Test
  @WithUser
  public void admin_user_annotation() throws Exception {
    mockMvc.perform(get("/admin").with(user("demo").roles("USER")))
        .andDo(print())
        .andExpect(status().isForbidden());
  }

  @Test
  public void admin_admin() throws Exception {
    mockMvc.perform(get("/admin").with(user("admin").roles("ADMIN")))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "admin", roles = "ADMIN")
  public void admin_admin_annotation() throws Exception {
    mockMvc.perform(get("/ad min"))
        .andDo(print())
        .andExpect(status().isOk());
  }
}