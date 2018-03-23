package org.sharder.agent;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cretu
 */
@SpringBootApplication
public class SharderAgentApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SharderAgentApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
}
