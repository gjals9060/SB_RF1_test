package co.worker.board.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.worker.board.board.model.BoardEntity;

/*
 * - JpaRepository<반환테이블객체, @ID 타입> 을 상속받는 인터페이스는 JPA 레퍼지토리로써 데이터 영속층에 해당합니다.
 * - 자동적으로 PK를 이용한 객체 Select나 전체 Select, 삭제, 수정을 지원하는 메소드가 생깁니다.
 * 
 * - public interface 이름 extends JpaRepository <엔티티 ID 유형>
 * <>안에는 엔티티 클래스 이름과 ID 필드 타입이 지정된다. 주의할 점은 "기본형의 경우, 래퍼 클래스를 지정한다는 점이다. 
 * 샘플 BoardEntity 클래스는 long 형을 ID를 지정하고 있기 때문에, 여기에서는 <BoardEntity, Long>라고 작성을 한다.
 * 또 하나 주의해야 할 것은 어노테이션이다. 클래스의 선언 앞에 "@Repository"라는 어노테이션이 붙여져 있다. 
 * 그러면 이 인터페이스가 JpaRepository임을 나타낸다. 이는 반드시 붙여 두어야 하는 것이다.
 * 
 * - jpaRepository의 주요 메서드		(참고 설명 : https://araikuma.tistory.com/329)
 * 
 * 	1. findAll : 전체 엔터티를 정리 한 List를 돌려준다.
 * 	2. getOne("ID") : ID를 지정하여 엔터티를 하나를 얻어온다. 인수에는 그 엔터티의 ID에 지정된 형식의 값이 들어간다.
 * 	3. saveAndFlush(엔티티) : 인수에 지정된 엔티티를 데이터베이스에 저장한다.
 * 	4. delete("ID") : 인수에 지정된 ID의 엔티티를 데이터베이스에서 삭제한다.
 * 	5. count() : 엔티티의 수를 int 값으로 반환한다.
 * 	
 */

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	
}
