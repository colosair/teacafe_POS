package teacafe_POS.seat;

import java.util.Scanner;

import teacafe_POS.common.CommonControllerInterface;

public class SeatController implements CommonControllerInterface {
	
	static Scanner sc = new Scanner(System.in);
	static SeatService service = new SeatService();
	
	@Override
	public void execute() {
		boolean isStop = false;
		
		while(!isStop) {
			f_selectAll();
			SeatView.menuDisplay();
			String job = sc.next();
			switch(job) {
			case "1"->{ f_insertSeat(); }
			case "2"->{ f_updateSeat(); }
			case "3"->{ f_deleteSeat(); }
			case "0"->{ isStop = true; }
			default -> { System.out.println("알림: 유효하지 않은 작업입니다."); }
			}
		}
	}
	
	public static void f_selectAll() {
		SeatView.display( service.selectAll() );
	}
	
	public static void f_selectById() {
		int dto_no = Integer.parseInt(dataInsert("조회할 좌석 번호>> "));
		SeatView.display( service.selectById(dto_no) );
	}
	
	public static String dataInsert(String column) {
		System.out.print(column);
		return sc.next();
	}
	
	public static SeatDTO makeDTO(String title) {
		int seat_no = Integer.parseInt(dataInsert(title + "좌석 번호>> "));
		String seated = dataInsert(title + "착석여부(Y, N)>> ");
		String reservation = dataInsert(title + "예약여부(Y, N)>> ");
		
		SeatDTO dto = SeatDTO.builder()
				.seat_no(seat_no)
				.seated(seated)
				.reservation(reservation)
				.build();
		
		return dto;
	}

	public static void f_insertSeat() {
		SeatDTO dto = makeDTO("추가할 ");
		SeatView.display( service.insertSeat(dto) + "건 추가" );
	}

	public static void f_updateSeat() {
		SeatDTO dto = makeDTO("수정할 ");
		SeatView.display( service.updateSeat(dto) + "건 수정" );
	}

	public static void f_deleteSeat() {
		int dto_no = Integer.parseInt(dataInsert("삭제할 좌석 번호 >> "));
		int result = service.deleteSeat(dto_no);
		if(result == 0) {
			SeatView.display( "해당 테이블이 존재하지 않습니다." );
		} else {
			SeatView.display( result + "건 삭제" );
		}
	}
	
	public static boolean f_checkEmptySeat(int dto_no) {
		SeatDTO seat = service.selectById(dto_no);
		if(seat != null && seat.getSeated().equals("N") && seat.getReservation().equals("N")) {
			return true;
		}
		return false;
	}
	
	public static boolean f_checkSeated(int dto_no) {
		SeatDTO seat = service.selectById(dto_no);
		if(seat != null && seat.getSeated().equals("Y")) {
			return true;
		}
		return false;
	}
	
	public static boolean f_checkReservation(int dto_no) {
		SeatDTO seat = service.selectById(dto_no);
		if(seat != null && seat.getReservation().equals("Y")) {
			return true;
		}
		return false;
	}
	
	public static int f_setSeated(int dto_no) {
		return service.setSeated(dto_no, "Y");
	}
	
	public static int f_clearSeated(int dto_no) {
		return service.setSeated(dto_no, "N");
	}
	
	public static int f_setReservation(int dto_no) {
		return service.setReservation(dto_no, "Y");
	}
	
	public static int f_clearReservation(int dto_no) {
		return service.setReservation(dto_no, "N");
	}
}
