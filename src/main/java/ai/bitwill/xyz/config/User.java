package ai.bitwill.xyz.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class User {
    @Value("${username}")
    private String userName;
    @Value("${mobile}")
    private String mobile;
}
