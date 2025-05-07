package teacafe_POS.pay;

import java.util.List;

import teacafe_POS.cart.CartController;

public class PayView {
	//여러 건 출력
	public static void display(List<PayDTO> dtolist) {
		System.out.println("\n------------------------------- 주문 목록 -------------------------------");
		if(dtolist.size() == 0) {
			System.out.println("                           존재하는 데이터가 없습니다.");
		} else {
			dtolist.stream().forEach(dto->display(dto));
		}
		System.out.println("-----------------------------------------------------------------------");
		
	}
	
	//한 건 출력
	public static void display(PayDTO dto) {
		int order_no = dto.getOrder_no();
		int total_price = dto.getTotal_price();
		String order_date = dto.getOrder_date();
		int seat_no = dto.getSeat_no();
		String pay_method = dto.getPay_method();
		String pay_state = "";
		
		switch(dto.getPay_state()) {
			case "Y" -> { pay_state = pay_method + "결제완료"; }
			case "N" -> { pay_state = pay_method + "결제취소"; }
			default -> { pay_state = "결제실패"; }
		}
		
		System.out.printf("[ 주문 %d / %d번 테이블 / %s / 결제금액: %d원 / %s ]\n",
				order_no, seat_no, order_date, total_price, pay_state);
		
		CartController.f_viewCart(order_no);
		
	}
}
