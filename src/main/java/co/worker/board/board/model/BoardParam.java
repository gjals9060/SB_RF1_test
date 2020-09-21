package co.worker.board.board.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * ### controller에서 파라미터로 받는 객체 ###
 * 
 * # 어노테이션
 * 	- @Min(0) : 최소값을 0으로 설정한다는 것.
 * 	- @NotEmpty : null과 "" 빈문자열 체크를 하고 있습니다.
 * 	- @Builder
 * 		=> !!! Builder Pattern !!!
 * 			빌더패턴이란?	복합 객체의 생성 과정과 표현 방법을 분리하여 동일한 생성 절차에서 서로 다른 표현 결과를 만들 수 있게 하는 패턴이다.
 * 						즉, 많은 필드를 가진 모델클래스에서 특정 필드들만으로 생성하고 싶을 때, 그 때마다 생성자를 정의하기는 비효율적!
 * 					좋은 예시/설명 : https://lemontia.tistory.com/483
 *  
 * # JPA 식별자
 * 		JPA는 엔티티들을 논리적인 공간인 영속성 컨텍스트에서 관리하는데, "엔티티를 구분할 수 있는 식별자"가 필요합니다. 
 * 		식별자가 되는 필드는 엔티티 클래스의 @Id 애노테이션을 통해 지정할 수 있습니다.
 * 		엔티티가 영속성 컨텍스트에 들어가 JPA에 관리되는 시점에는 반드시 식별자로 지정된 필드에 식별자 값이 할당되어 있어야 합니다.
 * # SEQUENCE(시퀀스) 전략
 * 		JPA에서 엔티티에 식별자를 할당하는 방법은 여러가지가 있지만 그 중 Oracle, DB2, H2, PostgreSQL 등에서 사용할 수 있는 시퀀스 기능을 활용한 SEQUENCE 전략이 있습니다. 
 * 		SEQUENCE 전략은 DB에서 시퀀스 기능을 지원해야만 사용가능한 전략이므로 DBMS에 종속적인 식별자 할당 전략입니다.
 * 
 */

@Getter
@Setter
@ToString
@Builder
public class BoardParam {
	
	@Min(0)
	private Long seq;
	@NotEmpty
	private String content;
    @NotEmpty
    private String username;
    @NotEmpty
    private String title;
	
}
