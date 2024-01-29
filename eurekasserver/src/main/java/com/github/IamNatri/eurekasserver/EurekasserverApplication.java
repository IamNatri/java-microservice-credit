package com.github.IamNatri.eurekasserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekasserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekasserverApplication.class, args);
	}

}
