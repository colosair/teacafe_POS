--------------------------------------------------------------
--테이블 제거
SELECT 'DROP TABLE ' || TABLE_NAME || ' CASCADE CONSTRAINTS;' FROM user_tables;

BEGIN
  FOR t IN (SELECT table_name FROM user_tables) LOOP
    EXECUTE IMMEDIATE 'DROP TABLE ' || t.table_name || ' CASCADE CONSTRAINTS';
  END LOOP;
END;
/
--------------------------------------------------------------
--테이블 생성
CREATE TABLE Menu (
	menu_no     number          ,
	menu_name   varchar2(30)    ,
	price	    number,
	temp	    varchar2(20),
	sold_out	varchar2(30)    DEFAULT 'N'        	
);

CREATE TABLE Seat (
	seat_no	    number          ,
	seated  	varchar2(20)    DEFAULT 'N',
	reservation	varchar2(20)    DEFAULT 'N'
);

CREATE TABLE Order_List (
	order_no	number          ,
    total_price number,
	order_date	date,
    seat_no     number          
);

CREATE TABLE Order_Detail (
	order_detail_no	number      ,
	order_no	    number		,
	menu_no	        number		,
    order_temp      varchar2(10),
	order_amount	number      DEFAULT 1		
);

CREATE TABLE Pay (
	pay_no	    number          ,
	order_no    number		    ,
	pay_method	varchar2(20)    ,
	pay_state	varchar2(20)    DEFAULT 'N'
);
--------------------------------------------------------------
--PK 제약조건 추가
ALTER TABLE Menu         ADD CONSTRAINT PK_MENU         PRIMARY KEY (menu_no);
ALTER TABLE Seat         ADD CONSTRAINT PK_SEAT         PRIMARY KEY (seat_no);
ALTER TABLE Order_List   ADD CONSTRAINT PK_Order_List   PRIMARY KEY (order_no);
ALTER TABLE Order_Detail ADD CONSTRAINT PK_ORDER_DETAIL PRIMARY KEY (order_detail_no);
ALTER TABLE Pay          ADD CONSTRAINT PK_PAY          PRIMARY KEY (pay_no);
--FK 제약조건 추가
ALTER TABLE Order_List   ADD CONSTRAINT FK_Order_List    FOREIGN KEY (seat_no)      REFERENCES Seat(seat_no)         ON DELETE SET NULL;
ALTER TABLE Order_Detail ADD CONSTRAINT FK_ORDER_DETAIL  FOREIGN KEY (order_no)     REFERENCES Order_List(order_no)  ON DELETE CASCADE;
ALTER TABLE Order_Detail ADD CONSTRAINT FK_ORDER_DETAIL2 FOREIGN KEY (menu_no)      REFERENCES Menu(menu_no)         ON DELETE SET NULL;
ALTER TABLE Pay          ADD CONSTRAINT FK_PAY           FOREIGN KEY (order_no)     REFERENCES Order_List(order_no)  ON DELETE CASCADE;
--UNQ 제약조건 추가
ALTER TABLE Menu         ADD CONSTRAINT UNQ_MENU        UNIQUE (menu_name);

--------------------------------------------------------------
----데이터 추가
--(메뉴 데이터: 메뉴번호, 메뉴명, 가격, 온도, 품절여부, 카테고리번호)
insert into menu values(1, '쌍화차', 9500, 'BOTH', 'N');
insert into menu values(2, '대추차', 9000, 'BOTH', 'N');
insert into menu values(3, '배숙', 8500, 'BOTH', 'N');
insert into menu values(4, '모과차', 8000, 'BOTH', 'N');
insert into menu values(5, '생강차', 8000, 'BOTH', 'N');
insert into menu values(6, '수정과', 8000, 'BOTH', 'N');
insert into menu values(7, '오미자차', 8000, 'BOTH', 'N');

insert into menu values(8, '작설차(우전)', 8000, 'HOT', 'N');
insert into menu values(9, '홍삼귤피차', 8000, 'HOT', 'N');
insert into menu values(10, '국화차', 7500, 'HOT', 'N');
insert into menu values(11, '제주메리골드차', 7500, 'BOTH', 'N');

insert into menu values(12, '유자차', 7500, 'BOTH', 'N');
insert into menu values(13, '매실차', 7500, 'BOTH', 'N');
insert into menu values(14, '오디차', 7500, 'ICED', 'N');
insert into menu values(15, '자몽차', 7500, 'HOT', 'N');

insert into menu values(16, '수제단팥죽', 10000, 'NULL', 'Y');
insert into menu values(17, '모둠떡', 8000, 'NULL', 'N');
insert into menu values(18, '인절미', 5500, 'NULL', 'N');
insert into menu values(19, '한과', 4000, 'NULL', 'N');

insert into menu values(20, '수제팥빙수', 15000, 'NULL', 'N');
insert into menu values(21, '오미자빙수', 14000, 'NULL', 'Y');
insert into menu values(22, '인절미빙수', 13000, 'NULL', 'N');
insert into menu values(23, '콩가루아이스크림', 8000, 'NULL', 'Y');
insert into menu values(24, '홍시슬러시', 8000, 'NULL', 'N');
insert into menu values(25, '유자슬러시', 8000, 'NULL', 'N');
insert into menu values(26, '미숫가루쉐이크', 8000, 'NULL', 'N');

insert into menu values(27, '아메리카노', 6000, 'BOTH', 'N');
insert into menu values(28, '카페라떼', 6500, 'BOTH', 'N');
insert into menu values(29, '코코아', 7000, 'BOTH', 'N');

--(좌석 데이터: 좌석번호, 착석여부, 예약여부);
insert into seat values(1, 'N', 'N');
insert into seat values(2, 'N', 'N');
insert into seat values(3, 'N', 'N');
insert into seat values(4, 'N', 'N');
insert into seat values(5, 'N', 'N');
insert into seat values(6, 'N', 'N');
insert into seat values(7, 'N', 'N');
insert into seat values(8, 'N', 'N');
insert into seat values(9, 'N', 'N');

--------------------------------------------------------------
commit;
--------------------------------------------------------------
