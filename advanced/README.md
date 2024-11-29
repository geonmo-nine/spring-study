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
