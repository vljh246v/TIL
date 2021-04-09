# CSRF 어택 방지 필터: CsrfFilter

- CSRF 어택 방지 필터
  - 인증된 유저의 계정을 사용해 악의적인 변경 요청을 만들어 보내는 기법
  - [위키 CSRF](https://ko.wikipedia.org/wiki/%EC%82%AC%EC%9D%B4%ED%8A%B8_%EA%B0%84_%EC%9A%94%EC%B2%AD_%EC%9C%84%EC%A1%B0)
  - 기본 원칙은 현재 브라우저가 서빙하는 곳과 서버 호스트가 동일해야 함
  - 타 도매인에 리소스를 제공하는 cors 가 있음 
  - 이러한 경우 csrf 에 노출 가능성이 높아짐
- 리소스 변경 요청일 경우 서버에서 만들어준 CSRF 토큰을 확인
- 