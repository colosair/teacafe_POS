package teacafe_POS.order_detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDetailDTO {
	private Integer order_detail_no;
	private Integer order_no;
	private Integer menu_no;
	private String order_temp;
	private Integer order_amount;
}
