package vtb.courses.stage2_task5.Configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import vtb.courses.stage2_task5.Service.CsiService;

@Configuration
public class CsiServiceConfig {

    @Bean
    public CsiService getCsiService(){
        return new CsiService();
    }

}
