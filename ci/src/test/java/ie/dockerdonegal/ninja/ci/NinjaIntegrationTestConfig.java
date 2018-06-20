package ie.dockerdonegal.ninja.ci;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NinjaIntegrationTestConfig {

    @Bean
    public TestRestTemplate createTestRestTempmlate() {
        return new TestRestTemplate();
    }
}
