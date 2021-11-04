package ua.itea.web.hw14.lesson14hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Lesson14HwApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lesson14HwApplication.class, args);
	}

}
