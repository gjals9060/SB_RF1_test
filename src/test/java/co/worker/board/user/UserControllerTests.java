package co.worker.board.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.worker.board.user.model.UserEntity;
import co.worker.board.user.model.UserParam;
import co.worker.board.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

/*
 * 
 * 	<Java의 주요 Logging Framework>
 * - native java.util.logging: 별로 사용하지 않는다.
 * - Log4J: 몇 년 전까지 사실상 표준으로 사용했다.
 * - Logback: Log4J 개발자가 만든 Log4J의 후속 버전, 현재 많은 프로젝트에서 사용되고 있다.
 * - SLF4J(Simple Logging Facade for Java): Log4J 또는 Logback과 같은 백엔드 Logger Framework의 facade pattern
 * - tinylog: 사용하기 쉽게 최적화된 Java용 최소형(75KB Jar) 프레임워크
 * 
 * 
 * 	## 테스트해서 계속 안됐던 이유!
 * 		먼저 스프링에서 Bean이란?
 * 		스프링컨테이너에 의해 생성된 자바객체를 Bean이라 함.
 * 		스프링 부트의 경우 @Controller, @Service, @Repository, @Bean등으로 필요한 빈을 등록하고 
 * 		@Autowired를 통해 주입받아 사용하는 것이 일반적.
 *  Service단에서 ModelMapper를 쓰기위해 객체선언을 했지만 Bean등록이 안되어있는 ModelMapper...
 *  ->> BoardApplication에서 @Bean으로 스프링빈 객체임을 선언해야함....!!!!!
 * 	
 * 
 * 
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Before
	public void insert() {
		for(int i=1; i<=10; i++) {
//			UserEntity entity = new UserEntity((long)i, "아이디"+i, "이름"+i, "패스워드"+i);
//			이러지 말자 Builder 써야지
			UserEntity entity = UserEntity.builder()
									.id("아이디"+i)
									.password("패스워드"+i)
									.name("이름"+i).build();
			userRepository.save(entity);
		}
	}
	
	@Test
	public void getUserAll() throws Exception {
		mockMvc.perform(get("/api/users/all")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void getUserOne() throws Exception {
		mockMvc.perform(
				get("/api/users/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void addUser() throws Exception {
		
		UserParam userParam = UserParam.builder()
									.id("추가아이디")
									.password("추가패스워드")
									.name("추가이름")
									.build();
		
		mockMvc.perform(
				post("/api/users/add")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsBytes(userParam)))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void editUser() throws Exception {
		
		UserParam userParam = UserParam.builder()
									.id("수정아이디")
									.password("수정패스워드")
									.name("추가이름").build();
		mockMvc.perform(
				put("/api/users/edit/3")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsBytes(userParam)))
				.andDo(print())
				.andExpect(status().isOk());

	}
	
	@Test
	public void deleteUser() throws Exception {
		
		mockMvc.perform(
				delete("/api/users/10")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		
		this.getUserAll();
	}
	
}







