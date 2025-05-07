package teacafe_POS.order_list;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderListDTO {
	private Integer order_no;
	private Integer total_price;
	private Date order_date;
	private Integer seat_no;
}
