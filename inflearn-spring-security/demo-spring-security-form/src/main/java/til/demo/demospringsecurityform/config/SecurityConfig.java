package til.demo.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  // 특정 url 기반 접근 설정을 하는 영역
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests() // 어떤 식으로 '인가' 할지
        .mvcMatchers("/", "/info", "/account/**").permitAll()
        .mvcMatchers("/admin").hasRole("ADMIN")
        .mvcMatchers("/user").hasRole("USER")
        .anyRequest().authenticated()
        .and()
      .formLogin() // form 로그인을 사용
        .and()
      .httpBasic();
  }

  // 해당 메서드를 사용해서 원하는 임의의  유저 정보를 설정할 수 있다.
  // {noop} : 패스워드 인코딩 방식으로, 접두사에 어떤 암호화 방식인지 표시해주어야 한다. {noop}는 암호화 하지 않았다.
  // UserDetailsService 를 구현했기 때문에 주석처리
  //  @Override
  //  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  //    auth.inMemoryAuthentication()
  //        .withUser("demo").password("{noop}123").roles("USER").and()
  //        .withUser("admin").password("{noop}!@#").roles("ADMIN");
  //  }
}
