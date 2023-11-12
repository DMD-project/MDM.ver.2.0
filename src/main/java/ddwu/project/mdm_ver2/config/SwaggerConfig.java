package ddwu.project.mdm_ver2.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Dmd.ver2.0 API 명세서")
                .version("v2.0")
                .description("DMD-project / Dmd.ver2.0 문서입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

}
