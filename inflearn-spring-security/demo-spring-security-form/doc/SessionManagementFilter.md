# 세션 관리 필터: SessionManagementFilter

- 세션 변조 방지 전략 설정 : sessionFixation

  - 공격자가 본인의 세션ID를 사용자에게 전달하고, 해당 세션ID를 가지고 정상적인 사용자가 접근을 한다면 공격자는 해당 세션으로 사용자의 정보에 접근할 수가 있다.
  - none
  - newSession
  - migrateSession (서블릿 3.0- 사용시 기본값)
  - changeSessionId(서블릿 3.1 + 컨테이너 사용시 기본값) : migrateSession 은 값을 복사하는 단계가 있는데 changeSessionId는 sessionId 만 변경하는 방식이다.

  ![설정 방법](https://lh3.googleusercontent.com/pw/ACtC-3cvyOPI5gVkStaRMzJcc7IK-fceevBMldoDSAWxHNEcuslTCSBP397AERy1fO-NekWXGGZt_R9QvddTh7yiGvzKnuDL25yTduYzf6bs3kdItOo6yrfcuw1nRsrqO69M6RciISITt2VafSSplTf5f4w2SQ=w765-h151-no?authuser=0)


- 유효하지 않은 세션을 리다이렉트 시킬 URL 설정
  - InvalidSessionUrl
  - 로그아웃 했을때 유요하지 않은 세션을 invalid 시킴
  ![설정 방법](https://lh3.googleusercontent.com/pw/ACtC-3dyREHjDT3FMx31BR5Vc4AH4B_RwoWv6qrA8u3LKNYgVIZMqi8yukTnP_D4VPH5wDR8SjMX_BtslXj6c50Tc49ovaEuFzrIfqU3wqrDHyftheOnVwDL3Sb1ogS_vCZ60DlSGn1kH0wb07zDTaKFhq7-jg=w532-h103-no?authuser=0)


- 동시성 제어 : maximumSessions
  - 추가 로그인을 막을지 여부 설정(기본값 false)
  ![설정 방법](https://lh3.googleusercontent.com/pw/ACtC-3dcb3WA1SGnk3Jjiyd_iSsn5Qg2OrIK12vSsA3YiOzhONEdBejczc9WeQpAru7dWL9qR8X8noCkmbnUmBZa9xgbZEStL8GB66AwgTw_kKRsWCIxo70bKEyjqrBS0o_oxMTkJUzRDYfSoKpAWFRJt_r_-A=w596-h171-no?authuser=0)

  - 시크릿 모드를 통한 테스트 예제
  ![예제](https://lh3.googleusercontent.com/pw/ACtC-3c1fqxzGWUEOIvT9rhAm30DhIDA7RwCsgvvd_6bTh6WQaLxU-VeCH_59Zc6FPjtdOp6coAJIkeWjLorLjsuI86onUSUnPgtdhaHRXZWaAzSesJQYicJIzXR_tTMXUZws-F4CiMNjPUxPrT8zT5mpJOwDQ=w855-h319-no?authuser=0)

- 세션 생성 전략 : sessionCreateionPolicy
  - IN_REQUIRED : 필요하면 만든다.
  - NEVBER : 스프링 시큐리티에서는 만들지 않고 기존에 있는 세션을 사용한다.
  - STATELESS : 세션을 사용하지 않는다.
  - ALWAYS
  ![설정 방법](https://lh3.googleusercontent.com/pw/ACtC-3eKVnIVW4YGpHbWFo-yL-9PpLuAAefhjSsUb2PWEywqUsS3z4smGd9f09fSKMQmFN5e6a-8rAiyc8LDmSn0gjJTLFayeTmSHhcV_ALhPFbXOuMmtXGmnST5wfUgxKkqVfrMbvTW11do22Q0f6RGTWtkvA=w722-h156-no?authuser=0)

