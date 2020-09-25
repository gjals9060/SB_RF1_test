package co.worker.board.user.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.worker.board.user.model.UserParam;
import co.worker.board.user.service.UserService;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
	
//	@Autowired
//	private UserService userService;
	
	private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
	
	//get - one
	@GetMapping("/{seq}")
	public Object getUserOne(@PathVariable("seq") @Min(1) Long seq) {
		return userService.getOne(seq);
	}
	
	//get - all
	@GetMapping("/all")
	public Object getUserAll() {
		return userService.getAll();
	}
	
	//post
	@PostMapping("/add")
	public Object add(@RequestBody @Valid UserParam userParam) {
		userService.add(userParam);
		return null;
	}
	
	//put
	@PutMapping("/edit/{seq}")
	public Object edit(@RequestBody @Valid UserParam userParam, @PathVariable("seq") @Min(1) Long seq) {
		userParam.setSeq(seq);
		userService.edit(userParam);
		return null;
	}
	
	//delete
	@DeleteMapping(value = "/{seq}")
	public Object delete(@PathVariable("seq") @Min(1) Long seq) {
		userService.delete(seq);
		return null;
	}
	
}
