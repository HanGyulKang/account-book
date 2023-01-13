# Account Book
### 가계부

---

## 목차
* [목표](https://github.com/HanGyulKang/account-book#목표)
* [사용 기술 내역](https://github.com/HanGyulKang/account-book#사용-기술-내역)
* [테이블 설계](https://github.com/HanGyulKang/account-book#테이블-설계)
* [제약조건](https://github.com/HanGyulKang/account-book#entity-제약조건)
* [회원 관리](https://github.com/HanGyulKang/account-book#회원-관리)
* [가계부 관리](https://github.com/HanGyulKang/account-book#가계부-관리)
* [가계부 상세 관리](https://github.com/HanGyulKang/account-book#가계부-상세-관리)

---

## 목표
1. 사용자는 이메일과 비밀번호로 회원가입을 할 수 있다.
2. 사용자는 회원 가입 후 로그인/로그아아웃을 할 수 있다.
3. 사용자는 로그인 후 가계부 서비스를 이용할 수 있다.
<br>가. 가계부에 오늘 사용한 금액과 메모를 입력할 수 있다.
<br>나. 가계부에 등록한 금액과 메모를 수정할 수 있다. 
<br>다. 원하는 가계부 내역을 삭제할 수 있다. 
<br>라. 삭제한 가계부 내역을 복구할 수 있다. 
<br>마. 이제까지 작성한 가계부 내용을 조회할 수 있다. 
<br>바. 가계부의 상세한 세부 내용을 조회할 수 있다. 
* 로그인하지 않은 고객은 __가계부 내역에 대한 접근 제한__ 처리가 되어야 한다.

---

## 사용 기술 내역
- Spring Boot
- MySQL 5.7
- JPA
- QueryDSL
- Spring Security
- JWT Token
- JUnit Test
- Docker

---

## 테이블 설계
| 내용                               | 설계                    |
|:---------------------------------|:----------------------|
| 한 명의 회원은 여러 개의 가계부 내용을 가질 수 있다.  | 회원테이블 1 : 가계부테이블 n    |
| 한 개의 가계부에는 여러 개의 상세 내용이 생길 수 있다. | 가계부테이블 1 : 가계부상세테이블 n |

* __DDL 파일 경로__
  <br>/study/account/src/main/resources/table
* __entity 작성__
  <br>/study/account/src/main/java/com/study/account/entity

---

## Entity 제약조건
1. Setter를 선언하지 않음으로 실수로 Dirty Checking을 통해 데이터가 update되는 일이 없도록 한다.
2. 데이터를 변경해야할 시 Entity 내에 이를 위한 method를 따로 만들어 해당 method로만 필요한 데이터가 update 되도록 한다.
3. 기본 생성자는 AccessLevel을 Proteced로 제한을 줘서 외부 패키지에서는 new로 엔티티 객체를 생성할 수 없도록 한다.

---

## 구현

---

### 회원 관리

1. __회원가입__ : [POST] /apis/v1/user/signup
* __계획__
  <br>유저의 고유 아이디와 이메일을 함께 관리하며 __고유 아이디(Auto increment)를 PK__ 로 두니,
  <br>__이메일 중복__ 이 가능하여 이를 막기 위해 회원 가입 전에 __이미 등록된 이메일인지 체크__ 가 필요했습니다.
  <br>__Spring Security__ 에 설정한 URL 접근 제한조치로 인해 가입한 __유저에게 권한을 부여__ 해야 했습니다.
  <br>가입 성공/실패에 따라 HttpStatus를 적절하게 내려줘야했습니다.

* __개발__
  <br>- 클라이언트에서 전달받은 이메일 주소가 데이터베이스에 이미 존재하는지 유무를 검증하도록 하였습니다.
  <br>- Spring Security가 제공하는 BCryptPasswordEncoder를 이용하여 비밀번호를 암호화 하였습니다.
  <br>- 유저에게 권한(ROLE) 값 부여 후 JPA로 데이터베이스에 저장하였습니다.
  <br>- 가입 성공/실패 여부에 따라 ResponseEntity 객체에 HttpStatus(200/400)를 적절하게 할당하여 return 하도록 하였습니다.
  <br>
  <br>

2. __로그인__ : [POST] /login
* __계획__
  <br>로그인이 성공하면 JWT 토큰을 발급해야합니다.
  <br>JWT 토큰을 생성할 때 필요한 Key, 만료시간 등을 관리하는 클래스가 필요합니다.
  <br>Spring Security의 기능을 활용하기 위해 UserDetails와 UserDetailsService를 구현한 구현체가 필요합니다.
  <br>인증(Authentication)과 인가(Authorization) 필터가 필요합니다.
* __개발__
  <br>- SecurityConfig에 각 API별 접근 권한을 설정하였습니다.
  <br>- 로그인 시 인증을 담당하는 Filter 클래스(__JwtAuthenticationFilter__)를 만들어 등록하였습니다.
  <br>- 로그인 Flow는 아래와 같습니다.
  <br>ㅤㅤ1. 로그인 시도
  <br>ㅤㅤ2. 인증 (로그인 검증) 필터 진입
  <br>ㅤㅤ3. 클라이언트로부터 받은 ID, PW로 임의 토큰 생성 후 Authentication 객체 생성 시도
  <br>ㅤㅤ4. 해당 객체 생성 시도 시 UserDetailsService 구현체의 loadUserByUsername 함수를 호출하여 데이터베이스의 유저 정보 조회
  <br>ㅤㅤ5. Spring Security가 유저 정보를 담고있는 UserDetails의 정보와, loadUserByUsername로 조회한 유저 정보를 비교하여 인증 절차 진행
  <br>- 인증 절차가 무사히 마무리 되면 __JWT 토큰을 발급__ 하여 __데이터베이스에 저장__ 하고, __Header에 담아 return__ 하도록 하였습니다.
  <br>- 이후 Spring Security에 의해 인가받은 권한이 필요한 API를 접근 시 JWT 토큰 검증 절차를 밟도록 검증 Filter 클래스(__JwtAuthorizationFilter__) 를 생성하여 등록하였습니다.
  <br>
  <br>


3. __로그아웃__ : [GET] /apis/v1/user/logout
* __계획__
  <br>로그아웃을 하면 발급받았던 토큰의 만료 여부를 떠나 서버 접근에 제한을 줘야했습니다.
* __개발__
  <br>- 서버 접근 시 만들어둔 JwtAuthorizationFilter에서 데이터베이스의 토큰과 클라이언트에서 보낸 토큰을 서로 비교하는 로직을 만들었습니다.
  <br>- 로그아웃 시 데이터베이스의 토큰을 삭제하여 JwtAuthorizationFilter에서 걸러내도록 구현하였습니다.

---

### 가계부 관리
1. __가계부 전체 조회__ : [GET] /apis/v1/main/account-book
* __계획__
  <br>조회한 데이터를 담을 객체로 Entity를 사용한다면 조회를 위해 구현해야할 로직이 Entity 클래스 내에 추가됨에 따라
  <br>지저분해지고 관리하기 어려울 수 있어서 __따로 만들어둔 DTO 객체를 return__ 하는게 좋을 것 같았습니다.
  <br>
  <br>상세 내역 조회가 따로 구현된 것 보다 전체 조회 시 한 번에 상세 내역까지 조회하는게 좋을 것 같았습니다.
  <br>조회 속도 향상을 위해 __페이징 처리__ 가 되어야했습니다.
  <br>추후 삭제 내역 조회도 동일한 쿼리를 사용하기 위해 'deleted' 변수를 생성하여 flag로 사용했습니다.
  <br>(전체 조회 시 0, 삭제 내역만 조회 시 1)
  <br>
  <br>위 계획을 모두 실행하기 위해서는 account_book 테이블에 account_book_detail 테이블이 __left join__ 이 걸려야하고 페이징 처리도 되어야 하기 때문에 __QueryDSL로 개발__ 하였습니다.
* __개발__
  <br>- 페이징 처리는 __Pageable 객체__ 를 이용하여 구현하였습니다.
  <br>DTO 객체에 __QueryProjection 설정__ 을 하여 생성자를 통해 데이터를 담을 수 있도록 구현하였습니다.
  <br>- __PageableExecutionUtils__ 을 사용하여 __쿼리 최적화__ 를 하였습니다. 
  <br>(조회되는 데이터 수가 limit보다 적거나 마지막 페이지에 왔을 경우 count 쿼리가 작동하지 않음)
  <br>
  <br>
2. __가계부 입력__ : [POST] /apis/v1/main/account-book
* __개발__
  <br>클라이언트로부터 데이터를 받아 가계부 Entity 객체에 Build하여 JPA로 저장하도록 간단하게 구현하였습니다.
  <br>
  <br>
3. __가계부 내용 수정(금액, 메모)__ : [GET] /apis/v1/main/account-book
* __계획__
  <br>금액 수정 API, 메모 수정 API, 금액과 메모 수정 API를 각각 두는 것보다 하나의 API에서 모두 처리하고 싶었습니다.
  <br>수정은 작성자만 수정이 가능하도록 만들어야 했습니다.
* __개발__
  <br>- 클라이언트에서 보낸 값이 있는 경우에만 데이터가 수정되도록 구현하였습니다.
  <br>(null인 값은 update하지 않고 데이터가 있는 경우에만 update)
  <br>
  <br>- 수정하려는 가계부의 ID로 가계부를 조회하여 접속한 유저가 주인이 맞는지 체크하도록 하였습니다. 
  <br>- 가계부 Entity 내에 금액과 메모 수정용 method를 만들어 __해당 method로만 데이터 변경__ 이 가능하도록 구현하였습니다.
  <br>- __update는__ repository 호출이 필요 없이 __dirty checking(변경 감지)으로 작동__ 하도록 구현하였습니다.
  <br>
  <br>
4. __삭제한 가계부 조회__ : [GET] /apis/v1/main/account-book/recycle-bin
* __개발__
  <br>가계부 전체 조회와 같은 로직을 탑니다. 
  <br>단, service 계층에서 repository에 deleted 값을 1로 던지도록 설계가 되어있어 삭제한 가계부만 조회되도록 구현하였습니다.
  <br>
  <br>
5. __가계부 삭제 또는 복구__ : [GET] /apis/v1/main/account-book/recycle-bin
* __계획__
  <br>가계부 삭제 여부를 테이블의 deleted값으로 구분하기 때문에 하나의 API에서 삭제와 복구를 모두 처리하고 싶었습니다.
  <br>가계부 __전체 조회 후 작동 시 삭제__ 만 할 것이고
  <br>__삭제한 가계부  조회 후 작동 시 복구__ 만 할 것이기 때문에 충분히 가능해보였습니다.
  <br>위 내용대로라면 가계부를 조회한 위치를 알고있는 클라이언트가 알맞는 값만 서버로 보내준다면 API 하나로 구현이 가능했습니다.
* __개발__
  <br>- 삭제 또는 복구하려는 가계부의 ID로 가계부를 조회하여 접속한 유저가 주인이 맞는지 체크하도록 하였습니다.
  <br>- 가계부 수정과 마찬가지로 가계부 Entity 내에 deleted값을 update할 수 있는 method를 만들었습니다.
  <br>- 역시 동일하게 dirty checking(변경 감지)를 통해 가계부의 deleted 값을 update 하도록 구현하였습니다.
  <br>
---

### 가계부 상세 관리
1. __가계부 상세 내용 입력__ : [POST] /apis/v1/detail/account-book
* __계획__
  <br>가계부에 등록된 금액을 세부적으로 어디에서 어떻게 사용했는지 저장하면 좋을 것 같았습니다.
* __개발__
  <br>- 금액을 지불한 상점, 상세 금액, 지불 타입(카드, 현금 등), 지불 일자를 저장하도록 테이블을 만들었습니다.
  <br>- 지불 타입은 Enum 클래스를 만들어 관리하도록 하였습니다.
  <br>- 클라이언트에서 받은 데이터를 가계부 상세 Entity에 Build하여 JPA로 저장하도록 구현하였습니다.
  <br>
  <br>
2. __가계부 상세 내용 삭제__ : [DELETE] /apis/v1/detail/account-book/{accountBookId}
* __개발__
  <br>- 클라이언트가 보낸 가계부 상세 내용 ID로 가계부 상세 내용을 조회하여 해당 데이터의 주인이 접속한 유저가 맞는지 검증하도록 구현하였습니다.
  <br>- 데이터의 주인 검증이 되었다면 정상적으로 데이터 삭제를 진행합니다.
  <br>
---

## 테스트 
* /test/java/com/study/account 이하에 각 기능별로 작성해두었습니다.
  
org.assertj.core.api.Assertions 를 사용하여 모든 값이 설계한대로 나오는지 테스트를 진행하였습니다.

---

