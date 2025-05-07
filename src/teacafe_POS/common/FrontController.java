package teacafe_POS.common;

import java.util.Scanner;

import teacafe_POS.cart.CartController;
import teacafe_POS.menu.MenuController;
import teacafe_POS.order_detail.OrderDetailController;
import teacafe_POS.order_list.OrderListController;
import teacafe_POS.pay.PayController;
import teacafe_POS.seat.SeatController;


public class FrontController {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		while(true) {
			System.out.println("======= 통합 주문 관리 시스템 =======");
			System.out.println("  1.키오스크 | 2.POS시스템 | 0.종료");
			System.out.println("================================");
			
			System.out.print("작업>> ");
			String job = sc.next();
			
			switch(job) {
			case "1" -> { orderMain(); }
			case "2" -> { if(adminLogin()) posMain(); }
			case "0" -> { System.out.println("\n=========== 시스템 종료 ==========="); return; }
			default -> { System.out.println("알림: 유효하지 않은 작업입니다."); }
			}
			System.out.println("\n");
		}
	}



	private static boolean adminLogin() {
		System.out.println("\n-------- 관리자 로그인 --------");
		System.out.print("관리자ID>> ");
		String id = sc.next();
		if(!id.equals("admin")) {
			System.out.println("알림: 로그인 실패! 아이디가 올바르지 않습니다.");
			return false;
		}
		
		System.out.print("관리자Password>> ");
		String password = sc.next();
		if(!password.equals("admin")) {
			System.out.println("알림: 로그인 실패! 페스워드가 올바르지 않습니다.");
			return false;
		}
		
		System.out.println("알림: 로그인 성공!");
		return true;
	}


	public static void orderMain() {
		//1.주문 생성
		int order_no = OrderListController.f_getNewOrderNo();
		OrderListController.f_insertNewOrder(order_no);
		PayController.f_insertNewPay(order_no);
		
		//2.좌석 선택
		int seat_no;
		while(true) {
			seat_no = selectSeat();
			if(seat_no == 0) {
				OrderListController.f_clearOrder(order_no);
				return;
			} else if(!SeatController.f_checkEmptySeat(seat_no)) {
				System.out.println("알림: 해당 좌석은 현재 이용하실 수 없습니다. 다시 선택해주세요.");
				continue;
			} else {
				break;
			}
		}
		
		OrderListController.f_updateOrderSeatNo(order_no, seat_no);	//주문내역(좌석번호) update
		SeatController.f_setSeated(seat_no);	//선택한 좌석을 사용중으로 변경
		
		//3.주문
		if(orderLoop(order_no)) {	//주문 성공
			return;
		} else { //주문 중단
			OrderListController.f_clearOrder(order_no);
			SeatController.f_clearSeated(seat_no);
			return;
		}
	}


	private static boolean orderLoop(int order_no) {
		while(true) {
			System.out.println("\n---------- 장바구니 목록 ----------");
			CartController.f_viewCart(order_no);
			viewTotalPrice(order_no);
			System.out.println("--------------------------------");
			System.out.println(" 1.추가 | 2.비우기 | 3.결제 | 0.취소 ");
			System.out.println("--------------------------------");
			System.out.print("작업>> ");
			String job = sc.next();
			
			switch(job) {
			case "1" -> { OrderDetailController.f_insertOrderDetail(order_no); }
			case "2" -> { OrderDetailController.f_deleteAllOrderDetail(order_no); }
			case "3" -> { if(PayController.f_updatePay(order_no) > 0) return true; }
			case "0" -> { return false; }
			default -> { System.out.println("알림: 유효하지 않은 작업입니다."); }
			}
		}
	}

	private static void viewTotalPrice(int order_no) {
		OrderListController.f_updateTotalPrice(order_no);
		int total_price = OrderListController.f_selectTotalPrice(order_no);
		if(total_price > 0) {
			System.out.println(" 총 금액:     " + total_price + "원");
		}
	}

	private static int selectSeat() {
		SeatController.f_selectAll();
		System.out.print("좌석 선택(취소는 0)>> ");
		int seat_no = Integer.parseInt(sc.next());
		
		return seat_no;
	}

	private static void posMain() {
		while(true) {
			System.out.println("\n--------- POS 시스템 ---------");
			System.out.println(" 1.테이블홈 | 2.매장관리 | 0.종료 ");
			System.out.println("----------------------------");
			System.out.print("작업>> ");
			String job = sc.next();
			
			switch(job) {
			case "1" -> { tableHome(); }
			case "2" -> { manageMain(); }
			case "0" -> { return; }
			default -> { System.out.println("알림: 유효하지 않은 작업입니다."); }
			}
		}
	}

	private static void tableHome() {
		while(true) {
			System.out.println("\n---------------- 테이블 홈 -----------------");
			System.out.println(" 1.주문받기 | 2.좌석비우기 | 3.예약석관리 | 0.이전");
			System.out.println("------------------------------------------");
			System.out.print("작업>> ");
			String job = sc.next();
			
			switch(job) {
			case "1" -> { orderMain(); }
			case "2" -> { clearSeat(); }
			case "3" -> { manageReservation(); }
			case "0" -> { return; }
			default -> { System.out.println("알림: 유효하지 않은 작업입니다."); }
			}
		}
	}
	
	private static void clearSeat() {
		int seat_no = selectSeat();
		if(seat_no == 0) return;
		
		if(SeatController.f_checkEmptySeat(seat_no)) {	//이미 비워져있음
			System.out.println("알림: 해당 좌석은 이미 비워져있습니다.");
		} else if(SeatController.f_checkReservation(seat_no)) {	//예약석임
			System.out.println("알림: 해당 좌석은 예약석입니다. 좌석예약을 해제해주세요.");
		} else {
			if(SeatController.f_clearSeated(seat_no) == 0) {	//존재하지 않는 좌석
				System.out.println("알림: 존재하지 않는 좌석입니다.");
			} else {	//비우기 성공
				System.out.println("알림: [" + seat_no + "번 테이블]을 성공적으로 비웠습니다.");
			}
		}
	}

	private static void manageReservation() {
		while(true) {
			SeatController.f_selectAll();
			System.out.println(" 1.좌석예약 | 2.예약해제 | 0.이전 ");
			System.out.println("-----------------------------");
			System.out.print("작업>> ");
			String job = sc.next();
			
			switch(job) {
			case "1" -> { 
				System.out.print("예약할 좌석>> ");
				int seat_no = Integer.parseInt(sc.next());
				
				if(SeatController.f_checkReservation(seat_no)) {	//이미 예약됨
					System.out.println("알림: 해당 좌석은 이미 예약되어있습니다.");
				} else if(SeatController.f_checkSeated(seat_no)) {	//사용중인 좌석
					System.out.println("알림: 해당 좌석은 현재 사용중입니다.");
				} else {
					if(SeatController.f_setReservation(seat_no) == 0) {	//존재하지 않는 좌석
						System.out.println("알림: 존재하지 않는 좌석입니다.");
					}  else {	//예약 성공
						System.out.println("알림: [" + seat_no + "번 테이블]이 성공적으로 예약되었습니다.");
					}
				}
			}
			case "2" -> { 
				System.out.print("예약해제할 좌석>> ");
				int seat_no = Integer.parseInt(sc.next());
				
				if(SeatController.f_checkEmptySeat(seat_no)) {	//이미 예약해제됨
					System.out.println("알림: 해당 좌석은 이미 비워져있습니다.");
				} else if(SeatController.f_checkSeated(seat_no)) {	//사용중인 좌석
					System.out.println("알림: 해당 좌석은 현재 사용중입니다.");
				} else {
					if(SeatController.f_clearReservation(seat_no) == 0) {	//존재하지 않는 좌석
						System.out.println("알림: 존재하지 않는 좌석입니다.");
					}  else {	//예약 성공
						System.out.println("알림: [" + seat_no + "번 테이블]이 성공적으로 예약 해제되었습니다.");
					}
				}
			}
			case "0" -> { return; }
			default -> { System.out.println("알림: 유효하지 않은 작업입니다."); }
			}
			
		}
	}
	
	

	private static void manageMain() {
		while(true) {
			System.out.println("\n--------------------- 매장 관리 ---------------------");
			System.out.println(" 1.주문내역 | 2.결제취소 | 3.메뉴관리 | 4.좌석관리 | 0.이전 ");
			System.out.println("---------------------------------------------------");
			System.out.print("작업>> ");
			String job = sc.next();
			
			switch(job) {
			case "1" -> { PayController.f_selectAll(); }
			case "2" -> { PayController.f_cancelPay(); }
			case "3" -> { 
				MenuController controller = new MenuController();
				controller.execute();
			}
			case "4" -> { 
				SeatController controller = new SeatController();
				controller.execute();
			}
			case "0" -> { return; }
			default -> { System.out.println("알림: 유효하지 않은 작업입니다."); }
			}
		}
	}
	
}
