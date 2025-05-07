package teacafe_POS.order_detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import teacafe_POS.common.DBUtil;

public class OrderDetailDAO {
	//field
	Connection conn;	//DB연결
	Statement st;		//SQL문을 DB에 전송
	PreparedStatement pst;
	ResultSet rs;		//SELECT 결과
	int resultCount;	//DML의 영향 받은 건수
	
	//0.DTO 만들기
	public OrderDetailDTO makeDTO(ResultSet rs) throws SQLException {
		OrderDetailDTO dto = OrderDetailDTO.builder()
				.order_detail_no(rs.getInt(1))
				.order_no(rs.getInt(2))
				.menu_no(rs.getInt(3))
				.order_temp(rs.getString(4))
				.order_amount(rs.getInt(5))
				.build();
		return dto;
	}
	
	//1.insertOrderDetail
	public int insertOrderDetail(OrderDetailDTO dto) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = """
					insert into order_detail 
					values( (SELECT NVL(MAX(order_detail_no), 0) + 1 FROM order_detail), ?, ?, ?, ? ) 
					""";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, dto.getOrder_no());
			pst.setInt(2, dto.getMenu_no());
			pst.setString(3, dto.getOrder_temp());
			pst.setInt(4, dto.getOrder_amount());
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
			
		return resultCount;
	}
	
	//2.deleteAllOrderDetail
	public int deleteAllOrderDetail(int order_no) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "delete from order_detail where order_no = ? ";
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



