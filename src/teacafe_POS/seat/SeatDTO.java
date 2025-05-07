package teacafe_POS.seat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SeatDTO {
	private Integer seat_no;
	private String seated;
	private String reservation;
}
