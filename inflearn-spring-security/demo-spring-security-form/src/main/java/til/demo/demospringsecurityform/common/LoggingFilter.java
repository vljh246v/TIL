package til.demo.demospringsecurityform.common;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.GenericFilterBean;

public class LoggingFilter extends GenericFilterBean {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start(((HttpServletRequest)request).getRequestURI());

    chain.doFilter(request, response);

    stopWatch.stop();
    logger.info(stopWatch.prettyPrint());
  }
}
