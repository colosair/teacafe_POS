package teacafe_POS.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MenuDTO {
	private Integer menu_no;
	private String menu_name;
	private Integer price;
	private String temp;
	private String sold_out;
}
