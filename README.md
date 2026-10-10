# teacafe_POS

순수 Java와 JDBC로 만든 찻집용 콘솔 POS입니다. 신한DS 금융SW 아카데미 5기 Java 학습 단계(2025-05)에서 혼자 설계·구현한 학습 프로젝트입니다.

프레임워크 없이 진입점 하나, 도메인별 계층, DB 스키마를 직접 만들어 보는 것이 목적이었습니다.

## 기능

| 모드 | 흐름 |
|---|---|
| 키오스크 | 주문 생성 → 좌석 선택 → 장바구니(추가·비우기) → 결제. 중간에 그만두면 주문과 좌석 상태를 되돌립니다 |
| POS(관리자) | 관리자 로그인 후 주문 내역 조회, 결제 취소, 메뉴·좌석 관리 |

좌석은 빈자리·사용 중·예약석 세 상태를 가지고, 상태를 바꾸기 전에 현재 상태를 확인해 잘못된 전이를 막습니다.

## 구조

```text
FrontController            진입점, 모드 선택, 관리자 로그인
├── 키오스크 흐름: 주문 → 좌석 → 장바구니 → 결제
└── POS 흐름: ControllerFactory → MenuController · SeatController (CommonControllerInterface.execute)
      각 도메인: Controller → Service → DAO → DTO (+ View)
            DBUtil: Oracle JDBC 연결과 자원 해제
```

| 패키지 | 내용 |
|---|---|
| `common` | `FrontController`, `ControllerFactory`, `CommonControllerInterface`, `DBUtil` |
| `menu` · `seat` · `cart` · `order_list` · `order_detail` · `pay` | 도메인별 Controller · Service · DAO · DTO · View |
| `src/찻집DB.sql` | Menu · Seat · Order_List · Order_Detail · Pay 5개 테이블과 FK 제약(`ON DELETE CASCADE / SET NULL`) |

## 기술

Java 17 · Oracle JDBC(ojdbc6) · Lombok · Eclipse 프로젝트 · 콘솔 입출력(Scanner)

## 실행

1. Oracle DB에 `src/찻집DB.sql`을 실행해 테이블을 만듭니다.
2. `common/DBUtil.java`의 접속 URL과 계정을 로컬 환경에 맞게 바꿉니다.
3. Eclipse에서 프로젝트를 열고 Lombok·ojdbc6 jar 경로를 `.classpath`에 맞게 지정한 뒤 `FrontController`를 실행합니다.

## 한계

학습 단계 결과물이라 다음 문제를 그대로 남겨 두었습니다.

- DB 접속 정보와 관리자 계정이 코드에 하드코딩되어 있습니다.
- 여러 테이블을 바꾸는 주문·결제 흐름에 트랜잭션 경계가 없습니다.
- 호출마다 커넥션을 새로 열고 닫습니다(커넥션 풀 없음).
- 컨트롤러가 static 메서드 방식과 `execute()` 인터페이스 방식으로 섞여 있습니다.
- 자동화된 테스트가 없습니다.

