# spring boot 첫번째 프로젝트

## 2024-10-26

- maven 과 gradle
    - 빌드와 의존성 관리를 도와주는 도구에는 maven과 gradle이 있다.
    - 이중 gradle 이 최근에 사용하는 디폴트 추세이다.
- spring boot 의 버전
    - snapshot 은 만들고 있는 버전이라는 얘기다
    - rc는 후보, m1 은 정식릴리즈 버전이 아니다.
    - 아무 postfix가 없는 정식 버전을 사용하는게 좋다
- group,artifact,name
    - group 회사명이나 조직이름. 도메인 이름을 반대로 쓰며 소문자와 점(`.`)으로 작성한다
    - artifact 빌드시 결과물의 이름. 케밥케이스로 작성한다
    - name 프로젝트의 이름 주로 설명용이다
    - `org.springframework.boot:spring-boot-starter-web` 의 경우
        - `org.springframework.boot` 가 group
        - `spring-boot-starter-web` 가 artifact
        - 따라서 `group:artifact:version` 과 같은 형식으로 프로젝트의 고유성을 가진다
- build.gradle.kts
  ```
  plugins {
      // Kotlin JVM 플러그인. gradle 과 같은 빌드도구에서 코틀린 컴파일러가 kotlin 코드를 jvm바이트코드(.class)로 빌드하도록 지원
      kotlin("jvm") version "1.9.25"
    
      // spring 과 통합을 지원하는 플러그인(ex.기본생성자,리플렉션지원) 
      kotlin("plugin.spring") version "1.9.25" 
    
      // 스프링부트 버전
      id("org.springframework.boot") version "3.3.5"
    
      // 스프링부트 버전과 호환되는 다른 의존모듈의 버전을 관리 
      id("io.spring.dependency-management") version "1.1.6"
  }
    
  // 자바 17버전 사용
  java {
      toolchain {
            languageVersion = JavaLanguageVersion.of(17)
      }
  }
    
  // Java의 @Nonnull은 Kotlin에서 non-null 타입으로, @Nullable은 nullable 타입으로 강제  
  kotlin {
      compilerOptions {
          freeCompilerArgs.addAll("-Xjsr305=strict")
      }
  }
  
  // 모든 테스트 작업에 대해 JUnit Platform을 사용하도록 설정
  tasks.withType<Test> {
      useJUnitPlatform()
  }
  ```

## 2024-10-27

- 예전에는 톰캣서버(was) 에 자바 어플리케이션을 설치했다.
- 현대에는 소스라이브러리에서 웹서버(톰캣)을 내장하여 사용한다
- 로그
    - slf4j 로깅 인터페이스
    - logback 구현체
- 테스트
    - junit 테스트 라이브러리
    - mockito 목 라이브러리
    - assertj 테스트 헬퍼
    - spring-test 스프링 통합테스트 지원
- welcome page 는 static/index.html 이다
- templates 하위에 viewResolver 가 맵핑할 .html 파일이 위치한다
- spring.io 에서 문서를 찾아 읽는 방법을 익혀야한다
- 자바 빌드 하고 실행
    - gradlew mac(linux계열) 에서 실행
    - gradlew.bat 윈도우에서 실행
  ```bash
  ~ ./gradlew clean build // build 폴더 날리고, 빌드
  ~ cd build/libs && java -jar {어플리케이션}.jar // 서버실행
  ```
- `./gradlew build` 시 생성되는 `/build` 파일 하위 디렉토리
  ```
  /classes // 컴파일된 .class 가 위치
  /kotlin // Kotlin 컴파일러의 출력이나 임시 파일들
  /libs // 빌드 후 생성된 jar 파일이 위치(-SNAPSHOT.jar 파일과 -plain.jar 파일(실행할수 없는 파일. 의존성으로 사용될때 사용))
  /resources // main/resources 의 정적 리소스가 복사됨
  /test-results // 테스트 결과 저장
  /report // 테스트 결과를 가지고 코드커버리지 리포트 같은 걸 생성
  ```
- `@ResponseBody` 가 컨트롤러 또는 컨트롤러 메서드에 붙으면 `viewResolver` 대신에 `HttpMessageConverter` 가 동작한다
    - 객체면 `MappingJackson2HttpJsonConverter`
    - 문자면 `StringHttpMessageConverter`

## 2024-10-28

- Test 는 기본적으로 `@Test` 매서드 단위로 TestClass 가 새 인스턴스로 만들어져 간섭하지 않는다.
- static (companion object) 를 사용하는 경우 `@AfterEach` 라이프사이클로 매서드마다 초기화 할 수 있다
- assertj 의 `assertThat`, `assertThrow<T>{...}` 를 사용하여 검증하기 좋다
- `@Autowired` 를 생성자에 걸어주면, 스프링컨테이너에 등록되어 있는 빈을 의존관계주입(DI)해준다
- `@Component` 어노테이션이 있는 클래스는 스프링이 뜰 때 컴포넌트 스캔에 의해 컨테이너에 빈으로 등록한다.
- `@Service` `@Repository` `@Controller` 모두 `@Component` 를 포함하고 있다
- 컴포넌트 스캔은 `@SpringBootApplication` 패키지의 하위를 기본적으로 진행한다(설정을 통해 경로지정은 가능한다)

## 2024-10-29

- 스프링빈으로 등록하는 방법은 컴포넌트스캔(자동)과 `@Bean` 을 이용한 수동 등록이 있다

## 2024-10-30

- jdbc 를 통해서 db와 연결을 할 수 있다.
- 인터페이스로 구현하면 다형성을 활용해 OCP(open-close principle)를 지킬 수 있다
- `@SpringBootTest`: 스프링컨테이너를 띄워서 통합테스트
- `@Transactional` 어노테이션을 테스트에 달면, 테스트 시작전에 트랜잭션을 실행하고, 테스트가 끝나면 항상 롤백한다. 이렇게하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
- 순수jdbc -> jdbcTemplate -> jpa -> spring data jpa

## 2024-10-31

- aop 를 통해서 공통관심사를 뽑아낼 수 있다 