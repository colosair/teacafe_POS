package teacafe_POS.cart;

import java.util.Scanner;

public class CartController {
	
	static Scanner sc = new Scanner(System.in);
	static CartService service = new CartService();
	
	public static void f_viewCart(int order_no) {
		CartView.display( service.viewCart(order_no) );
	}
}
