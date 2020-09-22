package co.worker.board.board.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.worker.board.board.model.BoardParam;
import co.worker.board.board.service.BoardService;

/*	# 어노테이션
 * 		@RequestMapping : 메서드 & 클래스 레벨에서 사용가능하나 대부분 클래스영역에서 사용.
 * 
 * 		@GetMapping : 메서드 레벨에서 사용.
 * 			- consumes :
 * 				HTTP 응답 헤더로 "Content-Type: application/json;charset=UTF-8"을 반환한다.
 * 				생략할 경우 메써드 리턴 타입에 따라 Content-Type을 자동으로 판단하여 반환한다.
 * 			- produces :
 * 				HTTP 요청 헤더가 "Content-Type: application/json;charset=UTF-8"인 것만 처리한다.
 * 				다른 값이 들어올 경우 org.springframework.web.HttpMediaTypeNotSupportedException을 발생시킨다.
 * 				HTTP 요청 헤더에 명시된 Content-Type은 HTTP 요청 바디의 형식을 의미한다. 즉, 서버에서는 JSON 형식의 바디만 처리하겠다는 의미이다.
 * 				참고로 GET 요청은 바디를 가지지 않으므로  파라미터를 명시할 필요가 없다
 * 
 * 		@RequastBody : HTTP 요청의 body 내용을 자바 객체로 매핑하는 역할을 합니다.
 * 
 * 		@ResponseBody : 자바 객체를 HTTP 요청의 body 내용으로 매핑하는 역할을 합니다.
 * 
 * 
 * 		@PathVariable : {value = "/{abc}"} 와 같이 url에서 각 구분자에 들어오는 값을 처리해야 할 때 사용합니다.	ex) @GetMapping(value = "/{seq}")
 * 		
 * 		@RequestParam : /read+?no=1와 같이 url이 전달될 때 no 파라메터를 받아오게 됩니다.
 * 		
 * 		@Vaild : 유효성 검사.
 * 
 * 		@validated : @Validated 어노테이션은 기본적으로 @Valid와 기능이 같지만, 속성 제약조건에 대한 그룹을 만들어 적용시킬 수 있다.
 * 				@Valid를 적용시킬때는 제약조건을 달아놓은 속성에 대해 전부 유효성 검사를 하게 되는데, 
 * 				만약 제약조건은 그대로 선언해놓되 원하는 속성만 유효성 검사를 하고싶은 경우에 사용하는 것이 @Validated 이다.
 * 				이 클래스에서는 @Min(1) 어노테이션을 사용하기 위함.
 * 				@Min(1) 어노테이션을 설정해둔 이유는 @PathVariable 어노테이션으로 받은 seq값이 0 이하일 수는 없기 때문이다.
 * 	
 * # 클래스 & 메서드
 * 		- ResponseEntity 
 * 			: @ResponseBody 어노테이션과 같은 의미로, ResponseEntity를 return Type으로 지정하면 JSON(default) 또는 xml Format으로 결과를 내려준다.
 *				또한, Http 의 상태코드를 함께 전송해주기 위해 사용한다.
 *
 *
 *
 * # 업데이트 변경사항
 *		-[#2 -> #3]  
 *			한정적인 형태인 ResponseEntity 객체를 사용하지 않고 직접 정의한 객체를 Response로 전달하기 위해 Object 타입으로 리턴하게 하였음.
 *
 */


@RestController
@Validated
@RequestMapping("/api/boards")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// get - select
	@GetMapping(
			value = "/all",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Object getAll() {
		return boardService.getBoard();
	}
	
	@GetMapping(
			value = "/{seq}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Object get(@PathVariable("seq") Long seq) {
		return boardService.getBoard(seq);
	}
	
	// post - insert
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object add(@RequestBody @Valid BoardParam boardParam) {
		boardService.add(boardParam);
		return null;
	}
	
	
	// put - update
	@PutMapping(value = "/{seq}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object edit(@PathVariable("seq") Long seq, @RequestBody @Valid BoardParam boardParam) {
		boardParam.setSeq(seq);
		boardService.edit(boardParam);
		return null;
	}
	
	// delete - delete
	@DeleteMapping(value = "/{seq}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object delete(@PathVariable("seq") Long seq) {
		boardService.delete(seq);
		return null;
	}
	
}
