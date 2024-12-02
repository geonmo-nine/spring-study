#### 2024-11-10

- 템플릿 매서드 패턴
    - 정의: 알고리즘의 골격을 부모 클래스에 정의하고, 자식 클래스에서 특정 부분만 재정의하여 문제를 해결
    - 결국 상속과 오버라이딩을 통한 다형성으로 문제를 해결
    - 단점: 상속을 통해 구현되기 때문에, 상속의 단점을 그대로 안고 간다.
        - 강결합, 부모클래스를 알고 있고 부모클래스의 변경 등 영향을 그대로 받음
    - 자식 클래스 입장에서는 부모클래스의 기능을 전혀 사용하지 않는데, 부모클래스를 알아야한다
- 전략 패턴
    - 정의: 변하지 않는 부분을 `Context`에 두고 변하는 부분을 `Strategy` 라는 인터페이스를 만들고 해당 인터페이스를 구현해서 문제를 해결
    - `Context` 는 변하지 않는 템플릿 역할을 하고, `Strategy` 는 변하는 알고리즘 역할을 한다
    - `Context` 는 `Strategy` 를 주입받아 실행한다.
    - 템플릿 매서드 패턴의 강결합 문제를 해결한다
    - 전략패턴에서, `Strategy` 는
        1. `Context` 생성시에 필드로 주입될 수도 있고

        2. `Context` 에서 특정 함수를 호출시 파라메터(람다, 익명클래스)로 넘길 수도 있다.
- 템플릿 콜백 패턴
    - 2번의 경우 템플릿 콜백 패턴이라고 부르며 jdbcTemplate, RestTemplate 이 템플릿 콜백 패턴으로 만들어져 있다
    - Context -> Template
    - Strategy -> Callback
- 프록시
    - 프록시는 실제 객체를 그대로 대체할 수 있어야한다
    - 할 수 있는 일
        - 접근제어
        - 캐싱
        - 지연로딩
        - 부가기능 추가
    - 프록시 패턴: 접근제어가 목적
    - 데코레이터 패턴: 부가기능이 목적

#### 2024-11-14

- 인터페이스 기반으로 설계하면, 프록시를 사용해서 원본 코드를 변경하지 않고 추가기능 구현이 가능하다
- 구체클래스 기반으로 설계하면, 상속을 통한 프록시 패턴으로 동일하게 원본 코드를 변경하지 않고 추가기능 구현이 가능하다
    - 인터페이스가 없어도 프록시 적용이 가능하다는 것을 집고 넘어가자
    - 단점
        - 부모클래스의 생성자를 호출해야한다
        - 클래스에 final 키워드가 붙으면 상속이 불가능하다
        - 매서드에 final 키워드가 붙으면 오버라이딩이 불가능하다
- 리플랙션
    - 클래스나 매서드의 메타정보를 동적으로 획득하고 코드도 동적으로 호출할 수 있다
    - 동적으로 클래스의 정보를 가져와야할 때(동적 클래스 로딩)
      ```kotlin
      val forName = Class.forName("hello.advanced.jdkdynamic.ReflectionTest\$Reflection") // 클래스의 경로를 동적으로 주입
      val method = forName.getMethod("callA") // 클래스 매서드를 동적으로 가져와서
      method.invoke(Reflection()) // 호출
      ```
    - 클래스는 컴파일 타임에 알고 있고, 매서드를 동적으로 가져올 떄
      ```kotlin
      val kClass = Reflection::class
      val method = kClass.functions.find { it.name == "callA" } as KFunction<*>
      method.call(Reflection())
      ```

- JDK 동적 프록시
    - 프록시 클래스를 인터페이스마다 만들지 않고, `java.lang.reflect.Proxy` 를 통해서 생성할 수 있다
    - 클래스 로더정보, 인터페이스, 그리고 핸들러 로직을 넣어준다.
    - 그러면 해당 인터페이스 기반으로 동적 프록시를 생성하고 결과를 반환한다
      ```kotlin
          val target = AImpl()
          val handler = TimeInvocationHandler(target)
  
          val proxy =
              Proxy.newProxyInstance(
                  AInterface::class.java.classLoader,
                  arrayOf(AInterface::class.java),
                  handler
              ) as AInterface
  
          proxy.call()
      ```
    - jdk 동적 프록시가 없을때는
        - Ainterface-AImpl-AImplProxy, BInterface-BImpl-BImplProxy 이렇게 만들었다면,
    - jdk 동적 프록시를 사용하면
        - Ainterface-AImpl, BInterface-BImpl 을 가지고 프록시를 만들 수 있다.
        - 공통의 로직은 InvocationHandler 로 옮긴다
    - 인터페이스가 필수다
- CGLIB
    - Code Generator Library : 바이트 코드 조작을 통해 동적 프록시를 생성
    - 인터페이스가 없이도 구체클래스에서 동적 프록시를 생성 할 수 있다
    - 이후에 설명할 ProxyFactory 가 이 기술을 편리하게 해주므로 대략적으로 감만 잡자
    - jdk 동적프록시의 invocationHandler 처럼 methodInterceptor 를 제공한다
    - 구체클래스를 상속하여 통해 프록시 객체를 생성한다
    ```kotlin
    val concreteService = ConcreteService()
    val enhancer = Enhancer()
    enhancer.setSuperclass(ConcreteService::class.java)
    enhancer.setCallback(TimeInterceptor(concreteService))
    val proxy = enhancer.create() as ConcreteService
    proxy.call()
    ```
- 프록시 팩토리
    - 타겟 클래스의 인터페이스 유무 따라 cglib 와 jdk 동적프록시를 자동으로 선택하여 프록시 객체를 생성해준다
    - 부가 기능을 advice 라는 개념으로 구현하고 내부적으로 invocationHandler, methodInterceptor 에서 advice 를 사용하여 부가기능을 구현하게 된다
    - advice
        - org.aopalliance.intercept 의 methodInterceptor 를 구현하면 된다
    ```kotlin
    val serviceImpl = ServiceImpl()
    val proxyFactory = ProxyFactory(serviceImpl)
    proxyFactory.addAdvice(TimeAdvice())
    val proxy = proxyFactory.proxy as ServiceInterface
    ```
    - 스프링 부트는 aop를 적용할 때 기본적으로 cglib 를 사용해서 구체클래스 기반으로 프록시를 생성한다
    - 포인트 컷: 어디에 적용할지 필터링 로직
    - 어드바이스: 부가로직
    - 어드바이저: 포인트컷+어드바이스
    - aop 적용수만큼 프록시를 만드는 거라 착각할 수 있는데, 하나의 프록시에 여러 어드바이스를 적용하는 것을 알고 있어야한다
    - 단점: 프록시 팩토리 설정 코드가 많아지고, 빈을 직접 등록해야한다. 컴포넌트 스캔으로 등록된 빈에는 적용하기 어렵다
- 빈 후처리기
    - 객체를 생성 후 빈으로 등록하기 전에 어떤 조작을 할 수 있는 매커니즘을 제공
    - `BeanPostProcessor` 을 구현한 객체를 빈으로 등록하여 사용
    - `@PostConstruct` 도 사실 스프링 내부적으로 해당 객체가 빈 후처리기를 통해 해당 어노테이션이 있으면 호출해 주는 로직으로 동작함
    - 컴포넌트스캔으로 바로 등록되는 경우는 프록시객체를 적용하기 어려운데, `BeanPostProcessor` 를 사용해서 프록시 적용 가능
- 스프링이 제공하는 빈 후처리기
    - `spring-boot-starter-aop` 를 추가하면 `aspectjweaver` 라는 `aspectj` 관련 라이브러리를 추가해주고 스프링부트가 자동으로 aop 관련 클래스를 빈으로 추가
    - 부트가 없던 시절에는 `@EnableAspectJAutoProxy` 를 직접 추가해줬어야하는데 이 부분을 부트가 자동을 추가해준다
    - `AnnotationAwareAspectJAuthProxyCreator` 자동 프록시생성기 가 위 설정으로 빈으로 등록되고 이름 그대로 자동으로 프록시를 생성해주는 빈 후처리기다
- 자동프록시 생성기 동작 과정
    1. 스프링이 빈 대상이 되는 객체생성
    2. 생성된 객체를 빈 후처리기에 전달
    3. 모든 advisor(pointcut + advice) 조회
    4. 클래스와 매서드를 보고 프록시 대상인지 체크
    5. 프록시 대상이면 프록시를 생성해서 리턴(대상이 아니면 프록시 하지 않음)
    6. 빈으로 등록
- 포인트컷은 2가지 동작에 사용된다
    1. 프록시 적용 여부 판단 - 빈 생성단계에서 클래스와 매서드를 보고 결정
    2. 어드바이스 호출 판단 - 런타임에 실제로 프록시가 호출되었을때 어드바이스를 호출할지 결정
- AspectJExpressionPointcut
    - 결국 실무에서는 포인트 컷으로 이것만 사용
    - aspectj 라는 aop 에 특화된 표현식
    - `execution(* hello.advanced.app.v1..*(..))`
        - `*` 반환 타입
        - `hello.advanced.app.v1` 패키지 이름
        - `..` 하위 패키지 모두
        - `*` 매서드 네임
        - `(..)` 모든 파라메터
- 스프링은 @AspectJ 어노테이션으로 매우 편리하게 어드바이저를 생성하도록 지원한다
- AnnotationAwareAspectJAuthProxyCreator 는
    - 자동으로 어드바이저를 찾아서 필요한 곳에 프록시를 생성 후 적용
    - @Aspect 를 찾아서 이것을 Advisor(pointcut+advice) 로 만듬
- Aop 적용방식
    - 컴파일 시점: .java -> .class 만드는 시점에 aspectj 컴파일러가 부가기능을 추가
        - aspectj 컴파일러가 필요해서 사용하지 않은
    - 클래스 로딩 시점: 자바를 실행시키고 .class 파일을 jvm 내부의 클래스 로더에 올릴 때 부가기능을 추가
        - 자바를 실행할때 `java -javaagent` 를 통해 클래스 로더기를 조작해야하는데 운영하기 번거로움
    - 런타임 시점(프록시): 컴파일이 끝나고 자바 클래스 로더에 .class 파일이 다 올라가서 프로그램이 시작된 이후에 자바 언어가 제공하는 범위 내에서 부가기능을 추가
        - 프록시를 사용해야하고, final 클래스가 안되는 등 일부 제약이 있으나, 별다른 설정이 필요하지 않음
- 용어정리
    - 조인포인트: 어드바이스가 적용되는 위치. 매서드로 한정
    - 포인트컷: 어드바이스가 적용되는 위치를 선별하는 기능
    - 타겟: 프록시가 적용되는 객체
    - 어드바이스: 부가기능
    - 에스팩트: 여러 어드바이스+여러 포인트컷의 모듈 @Aspect
    - 어드바이저: 하나의 어드바이스와 포인트 컷
    - 위빙: 포인트컷으로 결정한 타겟의 조인포인트에 어드바이스를 적용하는 것
    - aop프록시: aop기능을 구현하기 위해 만든 프록시 객체. 스프링aop프록시는 jdk동적프록시 또는 cglib프록시이다