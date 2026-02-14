## mini java web application server project
Java의 Socket API를 사용해 HTTP 요청/응답을 직접 처리하는
학습용 HTTP 웹 서버 프로젝트입니다.

### 구현내용
- HTTP/1.1 프로토콜 파싱: Request Line, Header, Body파싱 및 객체화
- 요청/응답 처리 분리: HttpRequest, HttpResponse 객체 설계를 통한 책임 분리
- 정적 파일 서빙 기능
- 동적 요청 처리: Controller인터페이스를 통한 로직 분리

### 추가 구현 예정
- Thread pool
- 쿠키/세션 구현
