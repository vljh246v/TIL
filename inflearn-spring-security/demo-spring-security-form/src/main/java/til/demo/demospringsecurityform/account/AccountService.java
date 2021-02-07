package til.demo.demospringsecurityform.account;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {


  // UserDetailsService 스프링 시큐리티에서 가지고 옴
  // DAO 를 통해 특정 저장소에 있는 유저 정보를 읽어 와서 인증을 할때 사용하는 인터페이스
  // 인터페이스 역할  및 조건 : username 을 가지고와서 데이터에서 가지고와서
  // UserDetails 라고 리턴해주는게 역할

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountRepository.findByUsername(username);

    if(Objects.isNull(account)){
      throw new UsernameNotFoundException(username);
    }

    return User.builder()
            .username(account.getUsername())
            .password(account.getPassword())
            .roles(account.getRole())
            .build();
  }

  public Account createNew(Account account) {
    account.encodePassword(passwordEncoder);
    return this.accountRepository.save(account);
  }
}
