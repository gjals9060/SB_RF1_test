package co.worker.board.board;

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

import co.worker.board.board.model.BoardEntity;
import co.worker.board.board.model.BoardParam;
import co.worker.board.board.repository.BoardRepository;

/*	# 어노테이션
 * 		@SpringBootTest
 * 		@RunWith(SpringRunner.class)
 * 		@AutoConfigureMockMvc	
 * 		@Before
 * 	
 * 	# 클래스 & 메서드
 * 		mockMvc : 웹 애플리케이션을 애플리케이션 서버에 배포하지 않고도 스프링 MVC의 동작을 재현할 수 있는 클래스
 * 		ObjectMapper : ObjectMapper 클래스를 이용해서 build된 param 객체를 JsonString 타입으로 변형시킵니다.
 * 					예를 들어, {"seq":null,"content":"추가내용","username":"추가유저","title":"추가제목"} 이런식으로 값을 넘깁니다.
 * 
 * 	# 테스트메서드 
 * 		perform()
 * 			:	DispathcherServlet에 요청을 의뢰
 * 			MockMvcRequestBuilders를 사용해 설정한 요청 데이터를 perform()의 인수로 전달
 * 			get / post / fileUpload 와 같은 메서드 제공
 * 			perform()에서 반환된 ResultActions() 호출
 * 
 * 		andDo() 
 * 			: 요청/응답 전체 메시지 확인하기. print() 메소드가 일반적.
 * 
 * 		andExpect() 
 * 			: perform() 메서드를 이용하여 요청을 전송하면, 그 결과로 ResultActions 객체를 리턴하는데, 
 * 			ResultActions는 응답 결과를 검증할 수 있는 andExpect() 메서드를 제공한다.
 * 
 * 		andReturn()
 * 	# 에러
 * 		initializationError : 테스트 시 src/test/java 내부와 src/main/java 내부의 각 위치가 동일해야함.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BoardControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Before
	public void insertBoard(){
	    for(int i =0; i<10; i++){
	        BoardEntity board = new BoardEntity();
	        board.setContent("내용"+i);
	        board.setTitle("제목"+i);
	        board.setUsername("테스트데이터");
	        boardRepository.save(board);
	    }
	}
	
	// CRUD 테스트
	@Test
	public void getBoard() throws Exception {
		mockMvc.perform(
				get("/api/boards/all")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}
	 
	@Test
	public void getBoardOne() throws Exception {
		mockMvc.perform(
				get("/api/boards/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void addBoard() throws Exception {
		BoardParam boardParam = BoardParam.builder()
				.content("추가내용")
				.title("추가제목")
				.username("추가유저")
				.build();
		
		mockMvc.perform(
				post("/api/boards/add")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(boardParam)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		this.getBoard();
	}
	
	
	@Test
	public void editBoard() throws Exception {
		
		BoardParam boardParam = BoardParam.builder()
				.content("수정내용")
				.title("수정제목")
				.username("수정유저")
				.build();
		
		mockMvc.perform(
				put("/api/boards/3")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.accept(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsString(boardParam)))
				.andDo(print())
				.andExpect(status().isOk());
		this.getBoard();
	}
	
	@Test
	public void deleteBoardOne() throws Exception {
		mockMvc.perform(
				delete("/api/boards/3")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk());
		this.getBoard();
	}
	
	//Bad_Request 테스트
    @Test //add
    public void board_BadRequest_add() throws Exception{
    	//username not null
        BoardParam param = BoardParam.builder().content("test")
                                    .title("test").build(); 
        
        // content not empty
        /*BoardParam param = BoardParam.builder().title("title")
                                    .content("")
                                    .username("woo").build(); 
       */ 
        mockMvc.perform(
        		post("/api/boards/add")
		            .accept(MediaType.APPLICATION_JSON_VALUE)
		            .contentType(MediaType.APPLICATION_JSON_VALUE)
		            .content(objectMapper.writeValueAsString(param)))
        		.andDo(print())
        		.andExpect(status().isBadRequest());
    }
	
    @Test
    public void board_BadRequest_getOne() throws Exception{
    	//0 이하인 경우 @Min 어노테이션으로 잡는지 확인.
        mockMvc.perform(
        		get("/api/boards/-1")
	                .accept(MediaType.APPLICATION_JSON_VALUE)
	                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void board_BadRequest_edit() throws Exception {
    	//username null
        BoardParam param = BoardParam.builder()
        							.content("test")
                                    .title("test")
                                    .build();
        
        // seq min 0
        /*BoardParam param = BoardParam.builder()
        							.content("test")
                                    .title("test")
                                    .username("gg")
                                    .build();*/
        
        mockMvc.perform(
        		put("/api/boards/-1")
	                .accept(MediaType.APPLICATION_JSON_VALUE)
	                .contentType(MediaType.APPLICATION_JSON_VALUE)
	                .content(objectMapper.writeValueAsString(param)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    
	
}








