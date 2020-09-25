package co.worker.board.user.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.worker.board.user.model.UserEntity;
import co.worker.board.user.model.UserParam;
import co.worker.board.user.repository.UserRepository;

@Service
public class UserService {
	
	/*
	 * BoardService 와 달라진 점 
	 * : Repository와 ModelMapper를 @Autowired하지않고 UserService의 생성자를 만듬
	 * 
	 */
	
	private UserRepository userRepository;
	private ModelMapper modelMapper;
	
	// 생성자 왜 명시하는지 궁금..?
	// ==> 왐마.... 이런 기본적인걸 넘어가고 있었다는게 슬픔...
	// Bean설명 보자... 설명 : https://cbw1030.tistory.com/54
	public UserService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
	
	//위의 객체선언과 생성자는 각 객체선언에 @Autowired를 붙인 것과 같은 의미를 가짐.
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private ModelMapper modelMapper;
	
	
	private <R, T>T sourceToDestinationTypeCasting(R source, T destination) {
		// 제네릭 메소드...
		modelMapper.map(source, destination);
		return destination;
	}
	
	//select - all
	public List<UserEntity> getAll() {
		List<UserEntity> userList = userRepository.findAll();
		return userList;
	}
	
	//select - one
	public Object getOne(Long seq) {
		Optional<UserEntity> userEntity = userRepository.findById(seq);
		return userEntity.isPresent() ? userEntity.get() : null;
	}
	
	//insert
	@Transactional
	public Object add(UserParam userParam) {
		return userRepository.save(sourceToDestinationTypeCasting(userParam, new UserEntity()));
	}
	//update
	@Transactional
	public Object edit(UserParam userParam) {
		return userRepository.save(sourceToDestinationTypeCasting(userParam, new UserEntity()));
				
		/*
		 * 	뜬금포 궁금...
		 * update 나 insert하는 작업이 둘다 repository.save() 메서드를 사용하는 데
		 * 둘의 차이는 어디서 발생하는가?
		 * ==> 	Entity 생성시 @Id Long seq 에 @GeneratedValue 를 부여했던 것.
		 * 		@GeneratedValue는 4가지 속성이 있는데 Default가 Auto->IDENTITY(다른 속성은 추가 어노테이션이 필요)임.
		 *  	이 IDENTITY는 "id값이 null"이면 자동 increase해줌.
		 *  	--> 즉, save() 시 @Id(pk)가 null이면 새로운 생성/ null이 아니면 수정.
		 */
	}
	
	//delete
	@Transactional
	public void delete(Long seq) {
		userRepository.deleteById(seq);
	}
}


















