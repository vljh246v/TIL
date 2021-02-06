package til.demo.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  // 특정 url 기반 접근 설정을 하는 영역
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests() // 어떤 식으로 '인가' 할지
        .mvcMatchers("/", "/info").permitAll()
        .mvcMatchers("/admin").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
      .formLogin() // form 로그인을 사용
        .and()
      .httpBasic();
  }
}
