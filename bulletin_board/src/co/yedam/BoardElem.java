package co.yedam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardElem {
	private String title;
	private String content;
	private String time;
	private String id;
}
