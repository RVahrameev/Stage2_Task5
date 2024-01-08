package vtb.courses.stage2_task5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "vtb.courses.stage2_task5.Repository")
@EnableTransactionManagement
public class Stage2Task5Application {

	public static void main(String[] args) {
//		AnnotationConfigApplicationContext applicationContext =
//				new AnnotationConfigApplicationContext("vtb.courses.stage2_task5", "vtb.courses.stage2_task5.Repository");
		SpringApplication.run(Stage2Task5Application.class, args);
	}

}
