package teacafe_POS.seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import teacafe_POS.common.DBUtil;


public class SeatDAO {
	//field
	Connection conn;	//DB연결
	Statement st;		//SQL문을 DB에 전송
	PreparedStatement pst;
	ResultSet rs;		//SELECT 결과
	int resultCount;	//DML의 영향 받은 건수
	
	//static final field ... (SQL Query)
	static final String SELECT_ALL = "SELECT * FROM seat";
	static final String SELECT_DETAIL = "SELECT * FROM seat WHERE seat_no = ?";
	static final String INSERT = "INSERT INTO seat VALUES( ?, ?, ? )";
	static final String UPDATE = "UPDATE seat " + 
			 					 "SET seated = ?, " + 
			 					 "reservation = ? " +
			 					 "WHERE seat_no = ?";
	static final String DELETE = "DELETE FROM seat WHERE seat_no = ?";
	
	//0.DTO 만들기
	public SeatDTO makeDTO(ResultSet rs) throws SQLException {
		SeatDTO dto = SeatDTO.builder()
				.seat_no(rs.getInt(1))
				.seated(rs.getString(2))
				.reservation(rs.getString(3))
				.build();
		return dto;
	}
	
	//1.SELECT_ALL
	public List<SeatDTO> selectAll() {
		List<SeatDTO> dtolist = new ArrayList<SeatDTO>();
		conn = DBUtil.getConnection();	//DB연결
		
		try {
			st = conn.createStatement();	//통로 뚫기
			rs = st.executeQuery(SELECT_ALL);		//쿼리문 실행 및 결과값 가져오기
			while(rs.next()) {
				SeatDTO dto = makeDTO(rs);
				dtolist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return dtolist;
	}
	
	//2.SELECT_DETAIL
	public SeatDTO selectById(int dto_no) {
		SeatDTO dto = null;
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(SELECT_DETAIL);	//통로 뚫기
			pst.setInt(1, dto_no);	//'?'에 매개변수로 받은 부서코드 setting
			rs = pst.executeQuery();		//쿼리문 실행 및 결과값 가져오기
			if(rs.next()) {
				dto = makeDTO(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return dto;
	}
	
	//3.INSERT
	public int insertSeat(SeatDTO dto) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(INSERT);	//통로 뚫기
			pst.setInt(1, dto.getSeat_no());
			pst.setString(2, dto.getSeated());
			pst.setString(3, dto.getReservation());
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLIntegrityConstraintViolationException e){
			SeatView.display("이미 존재하는 좌석입니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}

	//4.UPDATE
	public int updateSeat(SeatDTO dto) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(UPDATE);	//통로 뚫기
			pst.setString(1, dto.getSeated());
			pst.setString(2, dto.getReservation());
			pst.setInt(3, dto.getSeat_no());
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}
	
	//4-1.setSeated(착석여부 변경)
	public int setSeated(int dto_no, String val) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "update seat set seated = ? where seat_no = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setString(1, val);
			pst.setInt(2, dto_no);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}
	
	//4-2.setReservation(예약여부 변경)
	public int setReservation(int dto_no, String val) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "update seat set reservation = ? where seat_no = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setString(1, val);
			pst.setInt(2, dto_no);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}
	
	//5.DELETE
	public int deleteSeat(int dto_no) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(DELETE);	//통로 뚫기
			pst.setInt(1, dto_no);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLIntegrityConstraintViolationException e) {
			SeatView.display("해당 좌석에 앉아있는 손님이 존재합니다. 좌석을 먼저 비워주세요.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}
	
	//6.checkEmptySeat
}



