package ie.dockerdonegal.ninja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ie.dockerdonegal.ninja")
public class NinjaApplication {
    public static void main( String[] args ) {
        SpringApplication.run(NinjaApplication.class, args);
    }
}
