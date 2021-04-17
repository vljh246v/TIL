# 세션 관리 필터: SessionManagementFilter

- 세션 변조 방지 전략 설정 : sessionFixation

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
