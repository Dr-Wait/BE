package com.DrWait;

import com.DrWait.core.env.EnvLoaderApplicationContextInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		// .env 값 등록을 위한 커스텀 Initializer 등록
		app.addInitializers(new EnvLoaderApplicationContextInitializer());
		app.run(args);

		log.info("Good");
	}

}
