package mx.edu.uacm.revistaAcercate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EntityScan("mx.edu.uacm.revistaAcercate.dominio")
public class revistaAcercateApplication {

	public static void main(String[] args) {
		SpringApplication.run(revistaAcercateApplication.class, args);
	}

}
