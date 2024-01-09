package vtb.courses.stage2_task5.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vtb.courses.stage2_task5.Service.CsiService;

@Configuration
public class CsiServiceConfig {

    @Bean
    public CsiService getCsiService(){
        return new CsiService();
    }

}
