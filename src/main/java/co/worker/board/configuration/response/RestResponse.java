package co.worker.board.configuration.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 
 * 
 * 	@AllArgsConstructor : 이 애노테이션은 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성해줍니다.
 * 						ex) 
 * 							public RestResponse(int code, String message, T result) {
 *								super();
 *								this.code = code;
 *								this.message = message;
 *								this.result = result;
 *							}
 *							
 * 	@NoArgsConstructor  : 이 애노테이션은 파라미터가 없는 생성자를 생성합니다.
 * 						ex)
 *							public RestResponse() {}
 * 							
 * 	code 는 200, 400과 같은 HttpStatus값이 들어가고, 
 * 	message는 반환이나 에러내용이 들어갑니다. 
 *	result는 json으로 반환될 객체입니다.
 * 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<T> {
	
	private int code;
	private String message;
	private T result;
}
