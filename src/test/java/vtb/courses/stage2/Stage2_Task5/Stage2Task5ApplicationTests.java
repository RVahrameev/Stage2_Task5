package vtb.courses.stage2.Stage2_Task5;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class Stage2Task5ApplicationTests {

	class SimpleClass {
		int instancedId;
		int [] registerId;

		public SimpleClass() {
			this.instancedId = 777;
			this.registerId = new int[] {111,222,333};
		}
	}

	private ResponseEntity<SimpleClass> getResponce(){
		SimpleClass simpleClass = new SimpleClass();
		return ResponseEntity.status(HttpStatus.OK).body(simpleClass);

	}
	@Test
	void contextLoads() {
		System.out.println(getResponce());
	}

}
