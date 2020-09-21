package co.worker.board.board.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * - @Table : 객체를 DB 테이블처럼 인식시키는 JPA 어노테이션
 * - @Id : DB에서 PK라고 생각하면 된다.
 * - @GeneratedValue : 주 키의 값을 위한 자동 생성 전략(아래 4가지)을 명시하는데 사용한다.
 * 		IDENTITY : id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다.
 * 		SEQUENCE : @SequenceGenerator 필요
 * 		TABLE : @TableGenerator 필요
 * 		AUTO(default) : 위 3가지를 자동으로 지정.
 */

@Entity
@Getter
@Setter
@ToString
@Table(name = "BoardEntity")
public class BoardEntity {
	
	@Id
	@GeneratedValue
	Long seq;
	String content;
	String username;
	String title;
	
}
