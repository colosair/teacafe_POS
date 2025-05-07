package teacafe_POS.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import teacafe_POS.common.DBUtil;


public class MenuDAO {
	//field
	Connection conn;	//DB연결
	Statement st;		//SQL문을 DB에 전송
	PreparedStatement pst;
	ResultSet rs;		//SELECT 결과
	int resultCount;	//DML의 영향 받은 건수
	
	//static final field ... (SQL Query)
	static final String SELECT_ALL = "SELECT * FROM menu ";
	static final String SELECT_DETAIL = "SELECT * FROM menu WHERE menu_no = ? ";
	static final String INSERT = "INSERT INTO menu VALUES( ?, ?, ?, ?, ? )";
	static final String UPDATE = "UPDATE menu " + 
			 					 "SET menu_name = ?, " + 
			 					 "price = ?, " +
			 					 "temp = ?, " +
			 					 "sold_out = ? " +
			 					 "WHERE menu_no = ?";
	static final String DELETE = "DELETE FROM menu WHERE menu_no = ? ";
	
	//0.DTO 만들기
	public MenuDTO makeDTO(ResultSet rs) throws SQLException {
		MenuDTO dto = MenuDTO.builder()
				.menu_no(rs.getInt(1))
				.menu_name(rs.getString(2))
				.price(rs.getInt(3))
				.temp(rs.getString(4))
				.sold_out(rs.getString(5))
				.build();
		return dto;
	}
	
	//1.SELECT_ALL
	public List<MenuDTO> selectAll() {
		List<MenuDTO> dtolist = new ArrayList<MenuDTO>();
		conn = DBUtil.getConnection();	//DB연결
		
		try {
			st = conn.createStatement();	//통로 뚫기
			rs = st.executeQuery(SELECT_ALL);		//쿼리문 실행 및 결과값 가져오기
			while(rs.next()) {
				MenuDTO dto = makeDTO(rs);
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
	public MenuDTO selectById(int dto_no) {
		MenuDTO dto = null;
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
	public int insertMenu(MenuDTO dto) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(INSERT);	//통로 뚫기
			pst.setInt(1, dto.getMenu_no());
			pst.setString(2, dto.getMenu_name());
			pst.setInt(3, dto.getPrice());
			pst.setString(4, dto.getTemp());
			pst.setString(5, dto.getSold_out());
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLIntegrityConstraintViolationException e){
			MenuView.display("이미 존재하는 메뉴 번호입니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}

	//4.UPDATE
	public int updateMenu(MenuDTO dto) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(UPDATE);	//통로 뚫기
			pst.setString(1, dto.getMenu_name());
			pst.setInt(2, dto.getPrice());
			pst.setString(3, dto.getTemp());
			pst.setString(4, dto.getSold_out());
			pst.setInt(5, dto.getMenu_no());
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}
	
	//5.DELETE
	public int deleteMenu(int dto_no) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(DELETE);	//통로 뚫기
			pst.setInt(1, dto_no);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return resultCount;
	}

	//6.checkTemp
	public String checkTemp(int menu_no) {
		String temp = null;
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "select temp from menu where menu_no = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, menu_no);
			rs = pst.executeQuery();
			if(rs.next()) {
				temp = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return temp;
	}

	//7.checkSoldOut
	public boolean checkSoldOut(int menu_no) {
		boolean result = false;
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "select sold_out from menu where menu_no = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, menu_no);
			rs = pst.executeQuery();
			if(rs.next()) {
				String soldout = rs.getString(1);
				if(soldout.toUpperCase().equals("Y")) { result = true; }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
}



