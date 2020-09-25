package co.worker.board.board.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.worker.board.board.model.BoardEntity;
import co.worker.board.board.model.BoardParam;
import co.worker.board.board.model.BoardResult;
import co.worker.board.board.repository.BoardRepository;

/*
 * 
 * !!! Optional<> 
 * 	Optional로 객체를 감싸서 사용하시게 되면…
 * 	NPE를 유발할 수 있는 null을 직접 다루지 않아도 됩니다. 수고롭게 null 체크를 직접 하지 않아도 됩니다.
 * 	명시적으로 해당 변수가 null일 수도 있다는 가능성을 표현할 수 있습니다. (따라서 불필요한 방어 로직을 줄일 수 있습니다.)
 * 		=> .ifPresent : null인지 아닌지 boolean
 * 
 */

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private ModelMapper modelMapper;
	/*
	 * 	# ModelMapper이란?
	 * 	암시적으로 매핑해줌. 현 예제의 BoardEntity -> BoardResult는 필드가 동일하지만
	 * 
	 * 
	 * 		<소스 모델>				===============>>		<목적 모델>
	 * 	class Order {					ㅣ			class OrderDTO {
	 * 	  Customer customer;			ㅣ				String customerFirstName;
	 * 	  Address billingAddress;		ㅣ				String customerLastName;
	 * 	}								ㅣ				String billingStreet;
	 * 									ㅣ				String billingCity;
	 * 	class Customer {				ㅣ			}
	 * 	  Name name;					ㅣ
	 * 	}
	 * 	
	 * 	class Name {
	 * 	  String firstName;
	 * 	  String lastName;
	 * 	}
	 * 	
	 * 	class Address {
	 * 	  String street;
	 * 	  String city;
	 * 	}
	 * 	
	 * 	ModelMapper modelMapper = new ModelMapper();
	 * 	OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
	 * 	이러한 매핑도 가능.
	 * 
	 * 
	 */
	
	@Transactional
	public List<BoardResult> getBoard(){
		
		List<BoardEntity> entityList = boardRepository.findAll();
		List<BoardResult> results = entityList.stream().map(boardEntity -> {
			
			BoardResult boardResult = new BoardResult();
			modelMapper.map(boardEntity, boardResult);
            return boardResult;
            
		}).collect(Collectors.toList());
		
		/*
		 * - stream().map() 
		 * 맵(map)은 스트림 내 요소들을 하나씩 특정 값으로 변환해줍니다. 이 때 값을 변환하기 위한 람다를 인자로 받습니다.
		 * 스트림에 들어가 있는 값이 input 이 되어서 특정 로직을 거친 후 output 이 되어 (리턴되는) 새로운 스트림에 담기게 됩니다. 
		 * 이러한 작업을 맵핑(mapping)이라고 합니다.
		 * 
		 * - .collect(Collerctors.toList())
		 * 스트림에서 작업한 결과를 담은 리스트로 반환합니다. 
		 *  map 으로 각 요소의 이름을 가져온 후 Collectors.toList 를 이용해서 리스트로 결과를 가져옵니다.
		 * 
		 * 스트림에 대한 자세한 내용은 다시 공부.
		 */
		
		return results;
	}
	
	@Transactional
	public BoardResult getBoard(Long seq) {	//return 타입 : BoardResult <-> Object랑 비교 해보자
//		return boardRepository.findById(seq).map(BoardEntity -> {
//			BoardResult boardResult = new BoardResult();
//			boardResult.setContent(BoardEntity.getContent());
//			boardResult.setTitle(BoardEntity.getTitle());
//			boardResult.setUsername(BoardEntity.getUsername());
//			boardResult.setSeq(BoardEntity.getSeq());
//			return boardResult;
//		}).get();
//			==> 기존 setter를 이용한 매핑을 수정. -> modelMapper사용
//			==> 만약 필드가 많거나 관계형 객체일때  소스낭비를 크게 줄임
		return boardRepository.findById(seq).map(boardEntity -> {
			BoardResult boardResult = new BoardResult();
			modelMapper.map(boardEntity, boardResult);
			return boardResult;
		}).get();
	}
	
	@Transactional
	public void add(BoardParam boardParam) {
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setTitle(boardParam.getTitle());
		boardEntity.setContent(boardParam.getContent());
		boardEntity.setUsername(boardParam.getUsername());
		boardRepository.save(boardEntity);
	}
	
	@Transactional
	public void edit(BoardParam boardParam) {
		Optional<BoardEntity> boardEntity = boardRepository.findById(boardParam.getSeq());
		boardEntity.ifPresent(entity -> {
			modelMapper.map(boardParam, entity);
			boardRepository.save(entity);
		});
	}
	
	@Transactional
	public void delete(Long seq) {
		boardRepository.deleteById(seq);
	}
	
}
