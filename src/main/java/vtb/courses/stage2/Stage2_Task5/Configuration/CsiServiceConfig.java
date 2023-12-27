package vtb.courses.stage2.Stage2_Task5.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vtb.courses.stage2.Stage2_Task5.Repository.ProductRepo;
import vtb.courses.stage2.Stage2_Task5.Service.CsiService;

@Configuration
public class CsiServiceConfig {

    @Bean
    public CsiService getCsiService(){
        return new CsiService();
    }

}
