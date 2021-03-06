package til.demo.demospringsecurityform.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import til.demo.demospringsecurityform.account.AccountService;
import til.demo.demospringsecurityform.common.LoggingFilter;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  AccountService accountService;

  public AccessDecisionManager accessDecisionManager(){

    // Hierarchy 설정을 통해 ROLE_ADMIN이 ROLE_USER보다 상위라고 명시
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

    // 설정한 Hierarchy를 WebSecurityExpressionHandler에 설정
    DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy);

    // 설정한 handler를 voter에 설정
    WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
    webExpressionVoter.setExpressionHandler(handler);

    // voter 리스트 생성
    List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);

    // voter 리스트를 AccessDecisionManager에 셋팅
    return new AffirmativeBased(voters);

  }

  public SecurityExpressionHandler securityExpressionHandler() {
    // Hierarchy 설정을 통해 ROLE_ADMIN이 ROLE_USER보다 상위라고 명시
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

    // 설정한 Hierarchy를 WebSecurityExpressionHandler에 설정
    DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy);

    return handler;
  }

  // 특정 url 기반 접근 설정을 하는 영역
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);

    http.authorizeRequests() // 어떤 식으로 '인가' 할지
        .mvcMatchers("/", "/info", "/account/**", "/signup").permitAll()
        .mvcMatchers("/admin").hasRole("ADMIN")
        .mvcMatchers("/user").hasRole("USER")
        .anyRequest().authenticated()
        .expressionHandler(securityExpressionHandler());

    http.formLogin()
          .loginPage("/login") // form 로그인을 사용
          .permitAll();

    http.httpBasic();

    http.rememberMe()
        .useSecureCookie(true)
        .userDetailsService(accountService)
        .key("remeber-me-sample");


    http.logout()
        .logoutSuccessUrl("/");

    // TODO ExceptionTranslationFilter -> FilterSecurityInterceptor
    // TODO AuthenticationException -> AuthenticationEntryPoint
    // TODO AccessDeniedException -> AccessDeniedHandler

    http.exceptionHandling()
        .accessDeniedHandler(new AccessDeniedHandler() {
          @Override
          public void handle(HttpServletRequest request, HttpServletResponse response,
              AccessDeniedException accessDeniedException) throws IOException, ServletException {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
            String username = principal.getUsername();

            System.out.println(username + " is denied to access " + request.getRequestURI());
            response.sendRedirect("/access-denied");
          }
        });


    SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
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


  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
