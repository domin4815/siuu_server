package pl.edu.agh.siuu.meetings.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringFacebookConnectorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFacebookConnectorServiceApplication.class, args);
	}
}
