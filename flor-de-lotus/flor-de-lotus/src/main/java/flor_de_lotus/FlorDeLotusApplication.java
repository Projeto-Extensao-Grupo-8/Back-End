package flor_de_lotus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FlorDeLotusApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlorDeLotusApplication.class, args);
	}

}
