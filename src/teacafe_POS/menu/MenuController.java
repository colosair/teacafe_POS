package teacafe_POS.menu;

import java.util.Scanner;

import teacafe_POS.common.CommonControllerInterface;
import teacafe_POS.seat.SeatView;

public class MenuController implements CommonControllerInterface {
	
	static Scanner sc = new Scanner(System.in);
	static MenuService service = new MenuService();
	
	@Override
	public void execute() {
		boolean isStop = false;
		
		while(!isStop) {
			f_selectAll();
			MenuView.menuDisplay();
			String job = sc.next();
			switch(job) {
			case "1"->{ f_insertMenu(); }
			case "2"->{ f_updateMenu(); }
			case "3"->{ f_deleteMenu(); }
			case "0"->{ isStop = true; }
			default -> { System.out.println("알림: 유효하지 않은 작업입니다."); }
			}
		}
	}
	
	public static void f_selectAll() {
		MenuView.display( service.selectAll() );
	}
	
	public static void f_selectById() {
		int dto_no = Integer.parseInt(dataInsert("조회할 메뉴 번호>> "));
		MenuView.display( service.selectById(dto_no) );
	}
	
	public static String dataInsert(String column) {
		System.out.print(column);
		return sc.next();
	}
	
	public static MenuDTO makeDTO(String title) {
		int menu_no = Integer.parseInt(dataInsert(title + "메뉴 번호>> "));
		String menu_name = dataInsert(title + "메뉴명>> ");
		int price = Integer.parseInt(dataInsert(title + "가격>> "));
		String temp = dataInsert(title + "온도(Both, Hot, Iced, Null)>> ");
		String sold_out = dataInsert(title + "품절여부(Y, N)>> ");;
		
		MenuDTO dto = MenuDTO.builder()
				.menu_no(menu_no)
				.menu_name(menu_name)
				.price(price)
				.temp(temp)
				.sold_out(sold_out)
				.build();
		
		return dto;
	}

	public static void f_insertMenu() {
		MenuDTO dto = makeDTO("추가할 ");
		MenuView.display( service.insertMenu(dto) + "건 추가" );
	}

	public static void f_updateMenu() {
		MenuDTO dto = makeDTO("수정할 ");
		MenuView.display( service.updateMenu(dto) + "건 수정" );
	}

	public static void f_deleteMenu() {
		int dto_no = Integer.parseInt(dataInsert("삭제할 메뉴 번호 >> "));
		int result = service.deleteMenu(dto_no);
		if(result == 0) {
			SeatView.display( "해당 메뉴가 존재하지 않습니다." );
		} else {
			SeatView.display( result + "건 삭제" );
		}
	}
	
	public static String f_checkTemp(int menu_no) {
		return service.checkTemp(menu_no);
	}

	public static boolean f_checkSoldOut(int menu_no) {
		return service.checkSoldOut(menu_no);
	}
	
}
