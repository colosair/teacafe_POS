package teacafe_POS.seat;

import java.util.List;

public class SeatView {
	//여러 건 출력
	public static void display(List<SeatDTO> dtolist) {
		if(dtolist.size() == 0) {
			display("존재하는 데이터가 없습니다.");
		} else {
			System.out.println("\n---------- 좌석 목록 ----------");
			dtolist.stream().forEach(dto->display(dto));
			System.out.println("-----------------------------");
		}
	}
	
	//한 건 출력
	public static void display(SeatDTO dto) {
		System.out.printf("  [%d번 테이블]\t    ", dto.getSeat_no());
			
		if(dto.getSeated().equals("Y")) {
			System.out.print("(사용중)");
		} else if(dto.getReservation().equals("Y")) {
			System.out.print("(예약됨)");
		} else {
			System.out.print("사용가능");
		}
		
		System.out.println();
	}
	
	//메시지 출력
	public static void display(String message) {
		System.out.println("알림: " + message);
	}

	public static void menuDisplay() {
		System.out.println("\n----------- 좌석 관리 -----------");
		System.out.println(" 1.추가 | 2.수정 | 3.삭제 | 0.이전 ");
		System.out.println("-------------------------------");
		System.out.print("작업>> ");
	}
}
