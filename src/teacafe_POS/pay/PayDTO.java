package teacafe_POS.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PayDTO {
	private Integer order_no;
	private Integer total_price;
	private String order_date;
	private Integer seat_no;
	private String pay_method;
	private String pay_state;
}
