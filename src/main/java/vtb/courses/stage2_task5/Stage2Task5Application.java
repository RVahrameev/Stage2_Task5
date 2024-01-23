package vtb.courses.stage2_task5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "vtb.courses.stage2_task5.Repository")
@EnableTransactionManagement
public class Stage2Task5Application {

	public static void main(String[] args) {
		SpringApplication.run(Stage2Task5Application.class, args);
	}

}
