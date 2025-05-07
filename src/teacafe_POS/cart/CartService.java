package teacafe_POS.cart;

import java.util.List;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class CartService {
	
	CartDAO DAO = new CartDAO();
	
	//1.viewCart
	public List<CartDTO> viewCart(int order_no) {
		return DAO.viewCart(order_no);
	}
	
	
	
}
