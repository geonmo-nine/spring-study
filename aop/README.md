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
    @Before("allMember() && @annotation(annotation)")
    fun getArg4(annotation: MethodAop) {
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