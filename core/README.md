#### 2024-11-01

- 스프링의 역사
- 2000년 초 EJB(enterprise java bean)가 표준이었으나, 너무 복잡했음
- POJO(plain old java object)로 개발하자는 얘기가 나옴
- 당시 ejb 는 ejb entity bean 이라는 db를 다루는 기술도 있었음
- ejb+ejb entity bean 를 대체하기 위해서 spring+jpa(구현체는 hibernate)가 생겨남

- solid:
    - single responsibility: 단일 책임 원칙
    - open close principle: 확장에는 열려있고, 변경에는 닫혀있다(다형성)
    - liscov 치환: 상위타입을 구현한 하위타입은 의미상 같은 동작을 해야한다(ex. run 매서든데, swim 동작을 하면 안됨)
    - interface 분리: 인터페이스는 응집도있게 분리되어야한다
    - dependency inversion: 의존성 역전. 고수준 모듈(비지니스로직)이 저수준 모듈(구체화)에 의존하면 안되고 서로 추상화에 의존해야한다
        - 왜 inversion 인지?: "잘 설계된 객체 지향 프로그램의 종속성 구조는 기존의 절차적 방법에서 일반적으로 발생하는 종속성 구조와 관련하여 "반전" 되기 때문"

- 프래임워크: 내가 작성한 코드를 프로그램이 제어하고 실행
- 라이브러리: 작성된 코드를 내가 제어하고 실행

#### 2024-11-03

- 타입으로 빈을 조회할 때 매칭되는 빈이 복수개라면 오류가 나므로 이름과 함께 조회해야한다
- 부모 타입으로 빈을 조회하면, 자식타입의 빈들이 모두 조회된다
- `BeanFactory` <- `ApplicationContext` <- `AnnotationConfigApplicationContext` 로 상속구조를 가진다
- `BeanFactory`: 빈을 관리하고 조회하는 기능
- `ApplicationContext`: 빈팩토리 외 다른 인터페이스도 구현한다
    - 메세지소스를 활용한 국제화 기능
    - 로컬,개발,운영 등 환경변수
    - 어플리케이션의 이벤트
    - 파일 등 리소스 조회
- 싱글톤
    - 장점
        - 메모리 아낌
    - 단점
        - 지저분한 구현코드가 필요하다
        - 내부적으로 구체클래스를 사용하여(DIP 위반) 인스턴스를 static 영역에 저장한다
        - `private` 생성자로 외부에서 인스턴스 생성못하게 막아야한다
- 스프링 컨테이너가 개입하면, 단점을 모두 제거하고 장점만 살릴 수 있다
- 단, 싱글톤 스프링 빈은 항상 무상태로 만들어야한다.
    - set 은 항상 조심. get 을 위주로 한다
    - 값을 공유하지 않도록 지역변수, threadLocal 을 사용해야한다
- @Configuration 과 싱글톤 그리고 바이트코드 조작
    - 아래와 같은 코드가 있을때, `memberRepository` 는 두 번 호출될 것 같다.
    - 하지만 그러면, memberRepository 이 두개 생성되고 싱글톤이 될 수 없다.
      ```kotlin
      @Configuration
      class AppConfig {
          @Bean
          fun memberRepository() = MemoryMemberRepository()
    
          @Bean
          fun memberService() = MemberServiceImpl(memberRepository())
    
          @Bean
          fun orderService() = OrderServiceImpl(memberRepository(), discountPolicy())
      }
      ```
    - 이 문제를 해결 하기 위해서 스프링 내부적으로 CBLIB라는 라이브러리를 사용해 AppConfig 를 상속하는 프록시 객체를 컨테이너에 등록한다.
        - 프록시 객체는 빈이 싱글톤으로 생성되도록, 있으면 컨테이너에서 리턴 없으면 기존 AppConfig 의 생성 로직을 사용했을 것이다.
    - @Configuration 없이 @Bean 만 붙여도 되나?
        - 빈은 등록이 된다. 하지만 컨테이너에 AppConfig 가 직접 등록되기 때문에, CGLIB 가 적용되지 않고, @Bean 들이 싱글톤을 보장하지 않는다.
        - AppConfig 도 스프링에서 관리하는 빈이 되어야 빈들이 싱글톤임을 보장하기 때문에 @Configuration 을 붙이는 것이다.