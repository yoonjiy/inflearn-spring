# 스프링 핵심 원리 이해
[1. 객체 지향 설계와 스프링](#1.-객체-지향-설계와-스프링)

[2. 스프링 핵심 원리 이해 1 (예제 만들기)](#2.-스프링-핵심-원리-이해-1-(예제-만들기))

[3. 스프링 핵심 원리 이해 2 (객체 지향 원리 적용)](#2.-스프링-핵심-원리-이해-2-(객체-지향-원리-적용))

## 1. 객체 지향 설계와 스프링

### 스프링이란?
* 스프링 프레임워크, 스프링 부트 등을 포함한 스프링 생태계 전체를 아우르는 말.
* 스프링부트?
  * 스프링을 편리하게 사용할 수 있도록 지원
  * Tomcat 같은 웹서버를 내장, 별도의 웹서버를 설치할 필요 x
  * starter 종속성 제공으로 쉬운 빌드 구성 가능
  * 프로덕션 준비 기능 제공
  * 관례에 의한 간결한 설정 
* 스프링의 핵심
  * 스프링은 자바 언어 기반의 프레임워크로 객체 지향 언어가 가진 강력한 특징을 살려낼 수 있는 프레임워크.
### 좋은 객체 지향 프로그래밍이란?
* 객체 지향 프로그래밍
> 객체 지향 프로그래밍은 컴퓨터 프로그램을 명령어의 목록으로 보는 시각에서 벗어나 여러 개의 독립된 단위, 즉 "객체"들의 모임으로 파악하고자 하는 것이다. 각각의 객체는 메시지 를 주고받고, 데이터를 처리할 수 있다. 
객체 지향 프로그래밍은 프로그램을 **유연**하고 **변경이 용이**하게 만들기 때문에 대규모 소프트웨어 개발에 많이 사용된다.

* 유연하고 변경이 용이 --> 다형성

### 다형성
* 역할과 구현. 인터페이스와 구현 객체. 
>클라이언트는 대상의 역할(인터페이스)만 알면 된다.\
클라이언트는 구현 대상의 내부 구조를 몰라도 된다.\
클라이언트는 구현 대상의 내부 구조가 변경되어도 영향을 받지 않는다.\
클라이언트는 구현 대상 자체를 변경해도 영향을 받지 않는다.

객체를 설계할 때 역할과 구현을 명확히 분리

객체 설계시 역할(인터페이스)을 먼저 부여하고, 그 역할을 수행하는 구현 객체 만든다.

### 다형성의 본질
- **인터페이스를 구현한 객체 인스턴스를 실행 시점에 유연하게 변경할 수 있다.**
- **클라이언트를 변경하지 않고, 서버의 구현 기능을 유연하게 변경할 수 있다.**

***
### 좋은 객체 지향 설계의 5가지 원칙(SOLID)
- SRP: 단일 책임 원칙(single responsibility principle)
  - 하나의 클래스는 하나의 책임만 가져야 한다. 
- OCP: 개방-폐쇄 원칙 (Open/closed principle)
  - 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
  - 다형성 이용: 인터페이스를 구현한 새로운 클래스를 하나 만들어서 새로운 기능을 구현. -> 기존 인터페이스를 변경하지는 않지만, 새로운 구현체를 만들어 확장은 하고 있음.
  - OCP 원칙을 지키기 위해 DI, IoC 컨테이너 등 스프링 기술이 필요.
- LSP: 리스코프 치환 원칙 (Liskov substitution principle)
  - 프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 한다.
  - 다형성에서 하위 클래스는 인터페이스 규약을 다 지켜야 한다는 것. (ex) 자동차 엑셀을 밟으면 앞으로 가야한다는 규약이 자동차 인터페이스에 있어야 한다.)
- ISP: 인터페이스 분리 원칙 (Interface segregation principle)
  - 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.
- DIP: 의존관계 역전 원칙 (Dependency inversion principle)
  - 프로그래머는 “추상화에 의존해야지, 구체화에 의존하면 안된다.” 의존성 주입은 이 원칙을 따르는 방법 중 하나다.
  - 구현 클래스에 의존하지 말고, 인터페이스에 의존해야 한다. 

### 객체 지향 설계와 스프링
스프링은 다음 기술과 다형성 + OCP, DIP를 가능하게 지원
- DI: 의존관계 주입
- DI 컨테이너 제공

***
## #2. 스프링 핵심 원리 이해 1 (예제 만들기)

### 비즈니스 요구사항과 설계
- 회원
  - 회원 기능: 가입, 조회
  - 회원 엔티티: id, 이름, 등급(VIP, BASIC)
  - `회원 데이터 저장소는 미확정`
- 주문과 할인 정책
  - 회원 등급에 따라 할인 정책을 적용
  - `할인 정책은 정액 할인으로 할 지, 정율 할인으로 할 지 미확정` 

### 회원, 주문, 할인 도메인 설계
- 회원 도메인
1) MemberService 인터페이스와 MemberServiceImpl 구현 객체
2) MemberRepository 인터페이스와 MemoryMemberRepository, DbMemberRepository 구현 객체

❗ 이때 MemberServiceImpl은 `private final MemberRepository memberRepository = new MemoryMemberRepository();` 와 같이 인터페이스 뿐 아니라 구현까지 모두 의존하고 있음

- 주문 도메인
1) OrderService 인터페이스와 OrderServiceImpl 구현 객체 -> 회원 조회, 할인 적용 두 가지 기능
2) 회원 조회 - MemberRepository 인터페이스와 MemoryMemberRepository, DbMemberRepository 구현 객체
3) 할인 적용 - DiscountPolicy 인터페이스와 FixDiscountPolicy, RateDiscountPolicy 구현 객체

👍 **역할과 구현을 분리**해서 다형성을 적용한 설계. 자유로운 구현 객체의 조립(변경)이 가능해진다! 

***
## #3. 스프링 핵심 원리 이해 2 (객체 지향 원리 적용)

### 문제점 발견

✅ 역할과 구현을 충실하게 분리했다. 

✅ 다형성을 활용해 인터페이스와 구현 객체를 분리했다.

❌ 그런데...**OCP, DIP** 를 준수하지 않았다! ❌

현재 OrderServiceImpl 코드를 살펴보자.

```
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
```

❗ DIP 위반: 주문 서비스 클라이언트(OrderServiceImpl)은 DisCountPolicy 인터페이스와 FixDiscountPolicy라는 구체 클래스에 모두 의존하고 있다. (MemberRepository도 마찬가지)

❗ OCP 위반: 정액이 아닌 정율 할인 정책으로 기능을 확장해서 변경하려면 클라이언트 코드 역시 변경해야 한다.

문제를 해결하려면? **인터페이스에만 의존하도록 설계를 변경하자!**

```
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
```

❓ 그런데 구현체가 없다. -> NPE가 발생하지 않으려면 클라이언트인 OrderServiceImpl에 **구현 객체를 대신 생성 및 주입**해주어야 한다!

### 관심사의 분리
- 구현 객체를 생성하고 연결하는 책임을 가지는 별도의 설정 클래스 AppConfig를 만든다. 
- AppConfig는 실제 동작에 필요한 구현 객체를 생성해주고, 생성한 객체 인스턴스의 참조를 생성자를 통해서 주입해준다.
  - MemberServiceImpl -> memoryMemberRepository
  - OrderServiceImpl -> MemoryMemberRepository, FixDiscountPolicy

```
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
```
- 설계 변경으로 구체 객체가 아닌 추상화(인터페이스)에만 의존하고 있다. - DIP 준수
- MemberServiceImpl은 생성자를 통해 어떤 구현 객체가 주입될 지 알 수 없고, 구현 객체의 생성과 주입은 오직 AppConfig가 담당해 결정한다.
- MemberServiceImpl은 기능의 실행에만 집중하면 된다! 

❓ 새로운 할인 정책을 적용하고 싶다면

- AppConfig의 등장으로 애플리케이션은 **사용 영역**과 **구성 영역**으로 분리되었다.
- FixDiscountPolicy를 RateDiscountPolicy로 변경하고 싶다면, 구성 영역만 변경하면 된다. 즉, 사용 영역은 전혀 영향을 받지 않는다. - OCP 준수

### IoC 컨테이너, DI 컨테이너
- AppConfig처럼 객체를 생성하고 관리하며 의존 관계를 연결해주는 것을 IoC 컨테이너 또는 DI 컨테이너라고 한다.
- AppConfig를 스프링 기반으로 변경하려면 AppConfig에 @Configuration을, 각 메소드의 @Bean을 붙여 스프링 컨테이너에 빈으로 등록해주면 된다.
- 이제 스프링 컨테이너 ApplicationContext에서 필요한 스프링 빈을 찾아 사용할 수 있다.
