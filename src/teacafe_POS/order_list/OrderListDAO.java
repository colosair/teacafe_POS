package teacafe_POS.order_list;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import teacafe_POS.common.DBUtil;


public class OrderListDAO {
	//field
	Connection conn;	//DB연결
	Statement st;		//SQL문을 DB에 전송
	PreparedStatement pst;
	ResultSet rs;		//SELECT 결과
	int resultCount;	//DML의 영향 받은 건수
	
	//0.DTO 만들기
	public OrderListDTO makeDTO(ResultSet rs) throws SQLException {
		OrderListDTO dto = OrderListDTO.builder()
				.order_no(rs.getInt(1))
				.total_price(rs.getInt(2))
				.order_date(rs.getDate(3))
				.seat_no(rs.getInt(4))
				.build();
		return dto;
	}
	
	//1.getOrderNo
	public int getNewOrderNo() {
		int order_no = 0;
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "SELECT NVL(MAX(order_no), 0) + 1 from order_list ";
			st = conn.createStatement();	//통로 뚫기
			rs = st.executeQuery(sql);		//쿼리문 실행 및 결과값 가져오기
			if(rs.next()) {
				order_no = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return order_no;
	}
	
	//2.insertNewOrder(order_no)
	public int insertNewOrder(int order_no) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		try {
			String sql = "INSERT INTO order_list VALUES( ?, 0, sysdate, null ) ";
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
	
	//3.clearOrder(order_no)
	public int clearOrder(int order_no) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "delete from order_list where order_no = ? ";
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
	
	//4.updateOrderSeatNo(order_no, seat_no)
	public int updateOrderSeatNo(int order_no, int seat_no) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "update order_list set seat_no = ? where order_no = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, seat_no);
			pst.setInt(2, order_no);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}
	
	//5.updateTotalPrice(order_no)
	public int updateTotalPrice(int order_no) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = """
				UPDATE order_list
				SET total_price = (
					SELECT NVL(SUM(od.order_amount * m.price), 0)
					FROM order_detail od
					JOIN menu m ON od.menu_no = m.menu_no
					WHERE od.order_no = ?
				)
				WHERE order_no = ? 
				""";
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
	
	//6.selectTotalPrice(order_no)
	public int selectTotalPrice(int order_no) {
		int totalPrice = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "select total_price from order_list where order_no = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, order_no);
			rs = pst.executeQuery();		//쿼리문 실행 및 결과값 가져오기
			if(rs.next()) {
				totalPrice = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return totalPrice;
	}
	
}



