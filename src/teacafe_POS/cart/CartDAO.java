package teacafe_POS.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import teacafe_POS.common.DBUtil;


public class CartDAO {
	//field
	Connection conn;	//DB연결
	Statement st;		//SQL문을 DB에 전송
	PreparedStatement pst;
	ResultSet rs;		//SELECT 결과
	int resultCount;	//DML의 영향 받은 건수
	
	//0.DTO 만들기
	public CartDTO makeDTO(ResultSet rs) throws SQLException {
		CartDTO dto = CartDTO.builder()
				.rownum(rs.getInt(1))
				.menu_name(rs.getString(2))
				.order_temp(rs.getString(3))
				.order_amount(rs.getInt(4))
				.build();
		return dto;
	}
	
	//1.viewCart
	public List<CartDTO> viewCart(int order_no) {
		List<CartDTO> dtolist = new ArrayList<CartDTO>();
		Connection conn = DBUtil.getConnection();	//DB연결
		try {
			String sql = """
					select rownum, m.menu_name, od.order_temp, od.order_amount
					from order_detail od join menu m on(od.menu_no = m.menu_no)
                     					 join order_list ol on(od.order_no = ol.order_no)
					where od.order_no = ? 
					order by od.order_detail_no 
					""";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, order_no);
			rs = pst.executeQuery();		//쿼리문 실행 및 결과값 가져오기
			while(rs.next()) {
				CartDTO dto = makeDTO(rs);
				dtolist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return dtolist;
	}
	
}



