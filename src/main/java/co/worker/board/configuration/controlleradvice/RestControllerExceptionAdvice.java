package co.worker.board.configuration.controlleradvice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import co.worker.board.configuration.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

/*
 * 	# RestControllerExceptionAdvice
 *		- 리턴타입은 ErrorResponse 타입이다.
 *
 *	- @RestControllerAdvice : @RestConroller가 붙어있는 api컨트롤러에서 발생하는 예외를 캐치하겠다는 의미.
 *
 *	- @Slf4j
 *
 * 	- @ResponseStatus(HttpStatus) 	: 	해당 리스폰스의 타입을 BAD_REQUEST(400)으로 반환하도록 합니다.
 * 
 * 	- @ExceptionHandler(class) 		: 	해당 메소드가 잡아야할 Exception을 처리합니다. 
 */
@RestControllerAdvice
@Slf4j
public class RestControllerExceptionAdvice {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RuntimeException.class)
	public ErrorResponse handlerRuntimeException(RuntimeException e, HttpServletRequest req) {
		
		//서비스 로직에서 발생한 RuntimeException은 handlerRuntimeException() 메소드에서 처리하게 됩니다.
		log.error("================= Handler RuntimeException =================");
		return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "RuntimeException : " + e.getMessage());
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req){
		
		// MethodArgumentNotValidException : 
		//		@Valid 애너테이션으로 데이터를 검증하고, 해당 데이터에 에러가 있을 경우 예외 메세지를 JSON으로 처리하는 ExceptionHandler 처리 방법입니다.
        log.error("================= Handler MethodArgumentNotValidException =================");
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "MethodArgumentNotValidException : "+e.getMessage()
		);
    }
	
}
