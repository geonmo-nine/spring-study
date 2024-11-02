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

    