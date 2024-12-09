역사

- EJB가 있었는데, 이론은 좋으나 너무 어려웠다
- 로드존슨이 EJB없이 스프링의 초안이 되는 책을 출간

스프링이 잘 나가게 되면서, 생태계가 점점 커짐

- 결국 스프링이 거대하고 무거워짐
- 스프링이 사용하는 라이브러리들의 호환성, 버전 설정의 문제가 커짐
- 등록할 빈이 점점 많아짐

스프링 부트의 등장

- was(웹서버)내장
- 라이브러리 종속성 관리, 버전 관리
- 프로젝트 시작에 필요한 스프링과 외부 라이브러리의 빈을 자동 등록
- 환경에 따른 설정 공통화
- 모니터링을 위한 메트릭제공

순수한 자바 프로젝트 셋팅

- build.gradle 살펴보기
    - plugin 에 id(“war”) 를 넣고
    - dependency 로 "jakarta.servlet:jakarta.servlet-api:6.0.0" 를 추가한다

톰켓에 설치할 war 파일 준비하기

- src/main 하위에 webapp 디렉토리를 생성하고, index.html 를 추가한다
- src/main/java/myServlet/TestServlet.java 를 생성하고,
  ```java
  @WebServlet(urlPatterns = "/test")
  public class TestServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      System.out.println("TestServlet.service");
      resp.getWriter().println("TestServlet.service");
    }
  }
  ```
  를 추가한다
- ./gradlew build 하여 build/libs/~~~.war 파일을 만들어 낸다
- `~~~.war` 파일은 `jar -xvf ~~~.war` 로 압축 해제 할 수 있고
- 압축해제하면, WEB-INF, classes, lib 와 같은 디렉토리로 구성되어있다

jar 란

- 여러 클래스와 리소스를 묶어서 jar(Java Archive) 라고 부른다
- 이 파일은 jvm 위에서 직접 실행되거나 라이브러리로 제공될 수 있다
- 실행하려면 `java -jar abc.jar` 로 실행한다

war 란

- was에 배포할 때 사용되는 web application archive 파일이다
- jar 가 jvm 위에서 실행된다면 war 는 웹 어플리케이션 서버 위에서 실행된다
- html, 정적리소스, 클래스파일을 모두 포함한다
- war 구조를 지켜야한다
    - WEB-INF
        - classes : 실행 클래스파일
        - lib : 라이브러리
    - index.html
    - css,js 등등

war 배포하기

- 생성한 war 파일을 톰캣이_설치된_경로/webapp 하위에 ROOT.war 로 이름을 변경해서 넣는다
- 톰캣이_설치된_경로/bin 에서 ./shutdown.sh && ./startup.sh 으로 재기동
- http://localhost:8080 접속하여 확인

서블릿 컨테이너 초기화

- was를 실행하는 시점에 필요한 초기화 작업이 있다
- 서비스에 필요한 필터와 서블릿을 등록하고 스프링을 사용한다면 컨테이너를 만들고 서블릿과 스프링을 연결하는 디스패쳐 서블릿도 등록해야한다
- localhost:8080/hello -> servlet container(dispatcher servlet) -> spring container(hello controller) 순으로 연결된다

서블릿 컨테이너

- 등록방식
    - @WebServlet
    - 프로그래밍 방식

스프링 컨테이너 연결

- 프로그래밍 방식으로 서블릿 컨테이너를 등록하고, 스프링 컨테이너를 연결한다

WAR 배포 방식의 단점

- 톰캣 설치
- war 빌드하고 배포

