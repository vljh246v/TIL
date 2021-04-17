# 세션 관리 필터: SessionManagementFilter

- 세션 변조 방지 전략 설정 : sessionFixation

  - 공격자가 본인의 세션ID를 사용자에게 전달하고, 해당 세션ID를 가지고 정상적인 사용자가 접근을 한다면 공격자는 해당 세션으로 사용자의 정보에 접근할 수가 있다.
  - none
  - newSession
  - migrateSession (서블릿 3.0- 사용시 기본값)
  - changeSessionId(서블릿 3.1 + 컨테이너 사용시 기본값)

- 유효하지 않은 세션을 리다이렉트 시킬 URL 설정

  - InvaildSessionUrl

- 동시성 제어 : maximumSessions

  - 추가 로그인을 막을지 여부 설정(기본값 false)

- 세션 생성 전략 : sessionCreateionPolicy
  - IN_REQUIRED
  - NEVBER
  - STATELESS
  - ALWAYS
