package com.github.IamNatri.msclientes;


import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Lazy;


@SpringBootApplication
@EnableEurekaClient
public class MsclientesApplication {


	public static void main(String[] args) {

		SpringApplication.run(MsclientesApplication.class, args);
	}

}
