package teacafe_POS.order_list;

import java.util.Scanner;

public class OrderListController {
	
	static Scanner sc = new Scanner(System.in);
	static OrderListService service = new OrderListService();
	
	public static int f_getNewOrderNo() {
		return service.getNewOrderNo();
	}
	
	public static int f_insertNewOrder(int order_no) {
		return service.insertNewOrder(order_no);
	}
	
	public static int f_clearOrder(int order_no) {
		return service.clearOrder(order_no);
	}
	
	public static int f_updateOrderSeatNo(int order_no, int seat_no) {
		return service.updateOrderSeatNo(order_no, seat_no);
	}
	
	public static int f_updateTotalPrice(int order_no) {
		return service.updateTotalPrice(order_no);
	}
	
	public static int f_selectTotalPrice(int order_no) {
		return service.selectTotalPrice(order_no);
	}
	
}
