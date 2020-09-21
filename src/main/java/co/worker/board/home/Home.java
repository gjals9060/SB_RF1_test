package co.worker.board.home;

import lombok.Getter;
import lombok.Setter;

// #. lombok : model의 getter/setter, toString등을 어노테이션으로 적용가능하게 해줌.
//		-> 일반적으로는 @Data를 사용하고 상황에 따라 필요한 어노테이션만 적용한다.(불필요한 메소드 줄여 커버리지의 퍼센트를 높임.)

@Getter
@Setter
public class Home {
	private String name;
	private int age;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
