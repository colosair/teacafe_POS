package teacafe_POS.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartDTO {
	private Integer rownum;
	private String menu_name;
	private String order_temp;
	private Integer order_amount;
}
