package co.worker.board.configuration.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
 * 
 * 	ServiceExceptionAspect : 서비스 로직에서 에러가 발생할 경우 RuntimeException으로 모두 바꿔서 리턴하도록 함.( 서비스 로직에서만 )
 * 							=> RestControllerExceptionAdvice 클래스에서 RuntimeException을 총 관리.
 * 
 * 	흐름 이해 : 즉, 모든 서비스 실행 전  ServiceExceptionAspect이 실행되고 
 * 			서비스 로직 실행 중  예외 발생시 Runtime E로 넘김/예외없으면 패스.
 * 			runtime E는 RestControllerExceptionAdvice에서 따로 관리.
 * 
 */

@Component
@Aspect
public class ServiceExceptionAspect {

    @Around("execution(* co.worker.board.*.service.*.*(..))")
    public Object serviceExceptionHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
        	//에러없으면 그냥 반환.
            return joinPoint.proceed();
        }catch(Throwable e){
        	//에러 발생 시
            //서비스 로직 에러를 RuntimeException으로 컨트롤러에 전달.
            throw new RuntimeException(e);
        }
    }
}