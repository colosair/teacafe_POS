package teacafe_POS.pay;

import java.util.Scanner;

public class PayController {
	
	static Scanner sc = new Scanner(System.in);
	static PayService service = new PayService();
	
	public static String dataInsert(String column) {
		System.out.print(column);
		return sc.nextLine();
	}
	
	public static int f_insertNewPay(int order_no) {
		return service.insertNewPay(order_no);
	}
	
	public static void f_selectAll() {
		PayView.display( service.selectAll() );
	}
	
	public static int f_updatePay(int order_no) {
		int resultCount = 0;
		String pay_method = "NULL";
		
		while(true) {
			pay_method = dataInsert("결제수단(카드/현금)>> ");
			if(pay_method.equals("카드") || pay_method.equals("현금")) break;
			System.out.println("알림: 유효하지 않은 입력입니다.");
		}
		
		resultCount = service.updatePay(order_no, pay_method);
		if(resultCount > 0) {
			System.out.println("알림: 결제가 완료되었습니다.");
		}
		
		return resultCount;
	}
	
	public static int f_cancelPay() {
		int resultCount = 0;
		f_selectAll();
		int order_no = Integer.parseInt(dataInsert("취소할 주문번호(돌아가려면 0)>> "));
		if(order_no == 0) return 0;
		
		resultCount = service.cancelPay(order_no);
		if(resultCount > 0) {
			System.out.println("알림: 결제가 취소되었습니다.");
		} else {
			System.out.println("알림: 유효하지 않은 입력입니다.");
		}
		
		return resultCount;
	}
	
}
