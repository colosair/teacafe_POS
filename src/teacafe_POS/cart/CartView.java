package teacafe_POS.cart;

import java.util.List;

public class CartView {
	//여러 건 출력
	public static void display(List<CartDTO> dtolist) {
		if(dtolist.size() == 0) {
			System.out.println("      - 주문내역이 없습니다. -      ");
		} else {
			dtolist.stream().forEach(dto->display(dto));
		}
	}
	
	//한 건 출력
	public static void display(CartDTO dto) {
		String menu_name = dto.getMenu_name();
		switch(dto.getOrder_temp().toUpperCase()) {
			case "HOT" -> { menu_name += "(HOT)"; }
			case "ICED" -> {  menu_name += "(ICED)"; }
		}
		if(menu_name.length() < 4) menu_name += "   ";
		
		System.out.printf(" %d.%s\t%d개", 
				dto.getRownum(),
				menu_name,
				dto.getOrder_amount()	);
		System.out.println();
	}
	
	//메시지 출력
	public static void display(String message) {
		System.out.println("알림: " + message);
	}
}
