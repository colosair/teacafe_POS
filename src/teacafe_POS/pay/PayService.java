package teacafe_POS.pay;

import java.util.List;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class PayService {
	
	PayDAO DAO = new PayDAO();
	
	//0.insertNewPay
	public int insertNewPay(int order_no) {
		return DAO.insertNewPay(order_no);
	}
	
	//1.SELECT_ALL
	public List<PayDTO> selectAll() {
		return DAO.selectAll();
	}
	
	//2.updatePay
	public int updatePay(int order_no, String pay_method) {
		return DAO.updatePay(order_no, pay_method);
	}
	
	//3.cancelPay
	public int cancelPay(int order_no) {
		return DAO.cancelPay(order_no);
	}
	
}
