package co.worker.board.user.model;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserParam {

	private Long seq;
	@NotEmpty
	private String id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String password;
	
}
