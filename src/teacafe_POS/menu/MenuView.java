package teacafe_POS.menu;

import java.util.List;

public class MenuView {
	//여러 건 출력
	public static void display(List<MenuDTO> dtolist) {
		if(dtolist.size() == 0) {
			display("존재하는 데이터가 없습니다.");
		} else {
			System.out.println("\n---------- 메뉴 목록 ----------");
			dtolist.stream().forEach(dto->display(dto));
			System.out.println("-----------------------------");
		}
	}
	
	//한 건 출력
	public static void display(MenuDTO dto) {
		System.out.print(dto.getMenu_no() + "." + dto.getMenu_name() + 
				(((dto.getMenu_no() + "." + dto.getMenu_name()).length() > 6)?"\t ":"\t\t ") + 
				((dto.getSold_out().toUpperCase().equals("Y"))?"[품절]":dto.getPrice()) + " ");
		
		switch(dto.getTemp().toUpperCase()) {
		case "BOTH" -> { System.out.print("(H/I)"); }
		case "HOT" -> { System.out.print("(H)"); }	
		case "ICED" -> { System.out.print("(I)"); }
		case "NULL" -> {}
		}
		
		System.out.println();
	}
	
	//메시지 출력
	public static void display(String message) {
		System.out.println("알림: " + message);
	}

	public static void menuDisplay() {
		System.out.println("\n----------- 메뉴 관리 -----------");
		System.out.println(" 1.추가 | 2.수정 | 3.삭제 | 0.이전 ");
		System.out.println("-------------------------------");
		System.out.print("작업>> ");
	}
}
