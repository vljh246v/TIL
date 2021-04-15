# 시큐리티 관련 서블릿 스팩 구현 필터: SecurityContextHolderAwareRequestFilter

- 시큐리티 관련 서브릿 API를 구현해주는 필터
  - HttpServletRequest#authenticate(HttpServletResponse)
  - HttpServletRequest#login(String, String)
  - HttpServletRequest#logout()
  - HttpServletRequest#start(Runnable)

