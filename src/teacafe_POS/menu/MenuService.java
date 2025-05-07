package teacafe_POS.menu;

import java.util.List;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class MenuService {
	
	MenuDAO DAO = new MenuDAO();
	
	//1.SELECT_ALL
	public List<MenuDTO> selectAll() {
		return DAO.selectAll();
	}
	
	//2.SELECT_DETAIL
	public MenuDTO selectById(int dto_no) {
		return DAO.selectById(dto_no);
	}
	
	//3.INSERT
	public int insertMenu(MenuDTO dto) {
		return DAO.insertMenu(dto);
	}
	
	//4.UPDATE
	public int updateMenu(MenuDTO dto) {
		return DAO.updateMenu(dto);
	}
	
	//5.DELETE
	public int deleteMenu(int dto_no) {
		return DAO.deleteMenu(dto_no);
	}

	//6.checkTemp
	public String checkTemp(int menu_no) {
		return DAO.checkTemp(menu_no);
	}

	public boolean checkSoldOut(int menu_no) {
		return DAO.checkSoldOut(menu_no);
	}
	
}
