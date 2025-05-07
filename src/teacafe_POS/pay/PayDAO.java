package teacafe_POS.pay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import teacafe_POS.common.DBUtil;


public class PayDAO {
	//field
	Connection conn;	//DB연결
	Statement st;		//SQL문을 DB에 전송
	PreparedStatement pst;
	ResultSet rs;		//SELECT 결과
	int resultCount;	//DML의 영향 받은 건수
	
	//DTO 만들기
	public PayDTO makeDTO(ResultSet rs) throws SQLException {
		PayDTO dto = PayDTO.builder()
				.order_no(rs.getInt(1))
				.total_price(rs.getInt(2))
				.order_date(rs.getString(3))
				.seat_no(rs.getInt(4))
				.pay_method(rs.getString(5))
				.pay_state(rs.getString(6))
				.build();
		return dto;
	}
	
	//0.insertNewPay
	public int insertNewPay(int order_no) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		try {
			String sql = "insert into pay values( ?, ?, 'NULL', 'N' ) ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, order_no);
			pst.setInt(2, order_no);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}
	
	//1.selectAll
	public List<PayDTO> selectAll() {
		List<PayDTO> dtolist = new ArrayList<PayDTO>();
		conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = """
					select o.order_no, total_price, to_char(order_date, 'yyyy-mm-dd hh24:mi:ss'), seat_no, pay_method, pay_state
					from order_list o join pay p on (o.order_no = p.order_no)
					order by order_no 
					""";
			st = conn.createStatement();	//통로 뚫기
			rs = st.executeQuery(sql);		//쿼리문 실행 및 결과값 가져오기
			while(rs.next()) {
				PayDTO dto = makeDTO(rs);
				dtolist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return dtolist;
	}

	//2.updatePay
	public int updatePay(int order_no, String pay_method) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "update pay set pay_state = 'Y' where order_no = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, order_no);
			resultCount += pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
			
			sql = "update pay set pay_method = ? where order_no = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setString(1, pay_method);
			pst.setInt(2, order_no);
			resultCount += pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}
	
	//3.cancelPay
	public int cancelPay(int order_no) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "update pay set pay_state = 'N' where order_no = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, order_no);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}
	
}



