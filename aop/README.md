- `@Aspect` 를 사용하려면 `@EnableAspectJAutoProxy` 를 추가해야하지만, 스프링부트가 알아서 해준다
- 참고로 스프링에서는 aspectj 가 제공하는 어노테이션, 인터페이스만 사용하고 프록시 방식의 aop만 사용한다
- 실제 aspectj가 제공하는 컴파일, 로드타임 위버를 사용하지 않는다

- `@Around` 포인트컷 표현식을 넣을 수도 있지만 `@Pointcut` 을 사용해서 분리할 수도 있다.
    - 매서드이름과 파라메터를 합쳐서 포인트컷 시그니처 라고 부른다
        ```kotlin
        @Pointcut("execution(* advanced.aop.order..*(..))")
        fun allOrder() {}
  
        @Around("allOrder()")
        fun doLog() {}
        ```
    - 반환타입, 코드내용은 비워둔다
    - 여러 포인트컷은 && , || , ! 같은 조건문으로 사용가능하다
      ```kotlin
        @Around("allOrder() && allService()")
        fun doTransactin() {}
      ```
    - 외부 다른 패키지의 포인트 컷을 가져올때는 패키지명까지 적는다
      ```kotlin
      @Around("advanced.aop.order.aop.Pointcuts.allOrder()")
      fun doLog() {}
      ```
- aop 의 순서는 `@Order` 어노테이션으로 가능하다
    - 매서드에는 사용할 수 없고, 클래스 단위로 적용해야한다
    - 따라서 순서가 필요하다면 클래스단위로 찢어야한다
- 어드바이스 종류
    - `@Around`: 전후 처리
    - `@Before`: 조인포인트 실행 전
    - `@AfterReturning`:조인포인트 정상완료후
    - `@AfterThrowing`: 조인포인트에서 예외 던지는 경우
    - `@After`: 조인포인트 정상/예외 상관없이 실행(finally)
- pointcut 표현식에서 클래스자리에 인터페이스 또는 슈펴타입을 사용해도 구현클래스가 매칭된다
- 포인트컷 표현식 선언 종류
    - execution: 젤 범용적으로 사용가능
    - @annotaion: 주어진 어노테이션을 가지고 있는 매서드를 매칭
    ```kotlin
    // 이렇게 패키지경로를 전부다 입력하던가
    @Before("@annotation(advanced.aop.order.aop.exam.annotation.Trace)")
    fun doTrace(joinPoint: JoinPoint) {
    }
  
    // 함수의 변수로 받으면, 패키지를 경로를 생략할 수도 있다
    @Before("allMember() && @annotation(myAnnotation)")
    fun getArg4(myAnnotation: MethodAop) {
      println("getArg4 ${annotation.value}")
    }
    ```
    - @target, @within 클래스의 어노테이션을 매칭
    - args: 인자를 매칭할 수 있음. 인자를 aop 내부에서 사용할떄도 유용
    ```kotlin
    @Before("allMember() && args(arg,..)")
    fun getArg3(arg: String) {
        println("getArg3 $arg")
    }
    ```
    - args, @target, @within 지시자는 단독으로 사용하면 안된다. 이 지시자들은 런타임에 타입을 확인하기 때문에, 런타임에 확인하기 위해 스프링의 내부 빈들까지도 모두 프록시를 생성하게 되므로
      에러를 발생시킨다. 따라서 앞에 execution 을 사용해서 프록시 적용대상을 줄여줘야한다
- 스프링aop 프록시의 한계
    - 매서드에서 다른 내부 매서드 호출시, 프록시가 적용되지 않음
        - 해결방법: 자기자신 주입(setter로), 지연조회(ObjectProvider 로 빈조회), 구조변경이 있고, 구조변경이 권장된다.
    - 타입캐스팅
        - 인터페이스 기반 jdk동적프록시, 구체클래스기반 cglib 를 사용해서 프록시를 생성하는데, jdk동적 프록시 사용시 구체 클래스로 캐스팅하면 실패한다
        - 구체 클래스로 캐스팅할일이 없어보이는데 왜 이게 문제가 될까?
        - 이 문제는 의존성 주입할 때 허들로 작용한다. 의존성은 인터페이스로 받는게 정석이나 현실에서는 구체 클래스타입으로 받아야 하는 경우도
          있기 때문이다
    - cblib 방식의 단점
        - 대상 클래스에 기본 생성자 필요
            - 정의없으면 기본 생성자가 자동생성되기에 큰 의미는 없다
        - 생성자 2번 호출문제
            - 프록시 객체가 생성될때 부모 클래스의 생성자도 호출되게 된다. 따라서 실제 타겟클래스가 생성될때 1번 프록시가 생성될때 1번 총 2번의 생성자가 호출된다
        - final 키워드 클래스, 매서드 불가
            - 해당 키워드는 상속이 불가능함
            - 우리가 일반적인 서비스 코드 짤떄는 파이널을 잘 쓰지 않는다(코틀린은 기본이 파이널임ㅠ)
- 스프링의 해결책
    - cblib 를 스프링 내부에 함께 패키징
    - objenesis 라는 패키지로 기본 생성자없이 객체 생성 가능하게 함
    - 결론적으로 스프링2.0 부터는 구체클래스를 기반으로 프록시를 생성하는 cglib 를 디폴트로 사용한다