package co.worker.board.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
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
 * 	여기서 !!! @Builder와 @NoArgsConstructor를 함께 사용하면, 컴파일 시 에러가 발생한다.
 * 		==> @AllArgsConstructor를 같이 쓰면 해결.
 * 
 * 	@EqualsAndHashCode(of = "seq")
 * 		equals, hashCode 자동 생성.
 * 			equals :  두 객체의 내용이 같은지, 동등성(equality) 를 비교하는 연산자
 * 			hashCode : 두 객체가 같은 객체인지, 동일성(identity) 를 비교하는 연산자
 * 
 * 		자바 bean에서 동등성 비교를 위해 equals와 hashcode 메소드를 오버라이딩해서 사용하는데,
 * 		@EqualsAndHashCode어노테이션을 사용하면 자동으로 이 메소드를 생성할 수 있다.
 */

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "seq")
@Table(name = "UserEntity")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
	
	@Id
	@GeneratedValue
	private Long seq;
	private String id;
	private String name;
	private String password;
	
	
}
