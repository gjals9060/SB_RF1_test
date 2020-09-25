package co.worker.board.configuration.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import co.worker.board.configuration.response.RestResponse;

/*
 * 
 * 	### RestControllerAspect : RestController 역할의 클래스를 감싸는 역할
 * 		==>	흐름 방식
 * 			:	controller에 요청이 들어오면 
 * 			-> 	해당 요청을 처리하는 메서드 실행전 RestControllerAspect-restResponseHandler() 실행.
 * 				->  요청 메서드 실행.
 * 				->	예외 없다면 RestResponse {상태코드, 메시지, (joinPoint)요청 메서드}를 반환.
 * 			->	본래의 요청에 대한 응답 반환..
 * 
 * 	# 어노테이션 
 * 		@Component
 * 		
 * 		@Aspect : 해당 클래스에서 AOP를 활용할 수 있도록 합니다.
 * 
 * 		@Around : 대상 객체의 메서드 실행 전, 후 또는 예외 발생 시점에 공통 기능을 실행.
 */

@Component
@Aspect
public class RestControllerAspect {
	
	@Around("execution(* co.worker.board.*.controller.*.*(..))")	// 첫번째 *는 모든 접근제한자를 의미, *(..) : 모든 메소드 선택.
	public RestResponse<Object> restResponseHandler(ProceedingJoinPoint joinPoint) throws Throwable{
		/*
		 * 	- execution 안에있는 정규식에 해당하는 메소드들을  잡게됩니다.
		 *	- 모든 메소드의 시작은 restResponseHandler 라는 메소드에서 RestResponse 생성자에서 joinPoint.proceed() 메소드를 실행하고 컨트롤러로 향하게 됩니다.
		 *	- 모든 로직이 예외없이 실행되면 RestResponse 객체를 리턴하게 됩니다.
		 */
		return new RestResponse<Object>(HttpStatus.OK.value(),"success", joinPoint.proceed());
									// RestResponse : {"code" : "httpStatus 상태코드, "message" : "success", }
	}
	
}
