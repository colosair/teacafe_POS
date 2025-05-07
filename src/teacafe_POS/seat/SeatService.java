package teacafe_POS.seat;

import java.util.List;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class SeatService {
	
	SeatDAO DAO = new SeatDAO();
	
	//1.SELECT_ALL
	public List<SeatDTO> selectAll() {
		return DAO.selectAll();
	}
	
	//2.SELECT_DETAIL
	public SeatDTO selectById(int dto_no) {
		return DAO.selectById(dto_no);
	}
	
	//3.INSERT
	public int insertSeat(SeatDTO dto) {
		return DAO.insertSeat(dto);
	}
	
	//4.UPDATE
	public int updateSeat(SeatDTO dto) {
		return DAO.updateSeat(dto);
	}
	
	//4-1.setSeated(착석여부 변경)
	public int setSeated(int dto_no, String val) {
		return DAO.setSeated(dto_no, val);
	}
	
	//4-2.setReservation(예약여부 변경)
	public int setReservation(int dto_no, String val) {
		return DAO.setReservation(dto_no, val);
	}
	
	//5.DELETE
	public int deleteSeat(int dto_no) {
		return DAO.deleteSeat(dto_no);
	}
	
}
