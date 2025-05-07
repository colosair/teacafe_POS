package teacafe_POS.order_list;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class OrderListService {
	
	OrderListDAO DAO = new OrderListDAO();
	
	//1.getOrderNo
	public int getNewOrderNo() {
		return DAO.getNewOrderNo();
	}
	
	//2.insertNewOrder(order_no)
	public int insertNewOrder(int order_no) {
		return DAO.insertNewOrder(order_no);
	}
	
	//3.clearOrder(order_no)
	public int clearOrder(int order_no) {
		return DAO.clearOrder(order_no);
	}
	
	//4.updateOrderSeatNo(order_no, seat_no)
	public int updateOrderSeatNo(int order_no, int seat_no) {
		return DAO.updateOrderSeatNo(order_no, seat_no);
	}
	
	//5.updateTotalPrice(order_no)
	public int updateTotalPrice(int order_no) {
		return DAO.updateTotalPrice(order_no);
	}
	
	//6.selectTotalPrice(order_no)
	public int selectTotalPrice(int order_no) {
		return DAO.selectTotalPrice(order_no);
	}
		
}
