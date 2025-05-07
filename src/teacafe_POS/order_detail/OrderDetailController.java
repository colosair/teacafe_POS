package teacafe_POS.order_detail;

import java.util.Scanner;

import teacafe_POS.menu.MenuController;

public class OrderDetailController {

	static Scanner sc = new Scanner(System.in);
	static OrderDetailService service = new OrderDetailService();
	
	public static String dataInsert(String column) {
		System.out.print(column);
		return sc.nextLine();
	}

	public static int f_insertOrderDetail(int order_no) {
		MenuController.f_selectAll();
		
		int menu_no = Integer.parseInt(dataInsert("주문 메뉴번호>> "));
		if(MenuController.f_checkSoldOut(menu_no)) {
			System.out.println("알림: 해당 메뉴는 현재 품절입니다.");
			return 0;
		}
		String menu_temp = MenuController.f_checkTemp(menu_no);
		String order_temp;
		
		switch(menu_temp.toUpperCase()) {
			case "BOTH" -> { 
				while(true) {
					order_temp = dataInsert("온도(Hot/Iced)>> ").toUpperCase();
					if(order_temp.equals("HOT") || order_temp.equals("ICED")) break;
					System.out.println("알림: 유효하지 않은 입력입니다.");
				}
			}
			case "HOT" -> { order_temp = menu_temp; }
			case "ICED" -> { order_temp = menu_temp; }
			case "NULL" -> { order_temp = menu_temp; }
			default -> { System.out.println("알림: 유효하지 않은 숫자입니다."); return 0; }
		}
		
		int order_amount = Integer.parseInt(dataInsert("주문수량>> "));
		if(order_amount <= 0) {
			System.out.println("알림: 유효하지 않은 숫자입니다."); return 0;
		}
		
		OrderDetailDTO dto = OrderDetailDTO.builder()
				.order_no(order_no)
				.menu_no(menu_no)
				.order_temp(order_temp)
				.order_amount(order_amount)
				.build();
		
		return service.insertOrderDetail(dto);
	}
	
	public static int f_deleteAllOrderDetail(int order_no) {
		int deleteCount = service.deleteAllOrderDetail(order_no);
		if(deleteCount > 0) {
			System.out.println("알림: 장바구니를 성공적으로 비웠습니다.");
		} else {
			System.out.println("알림: 제거할 주문목록이 존재하지 않습니다.");
		}
		return deleteCount;
	}

}
