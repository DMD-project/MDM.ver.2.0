package ddwu.project.mdm_ver2.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("M2DM.ver2.0 API 명세서")
                .version("v2.0")
                .description("M2DM-project / M2DM.ver2.0 문서입니다.");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("bearer-key");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", securityScheme))
                .info(info)
                .addSecurityItem(securityRequirement);
    }
}
