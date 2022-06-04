package br.com.fourcamp.fourstore.FourStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class FourStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FourStoreApplication.class, args);
	}

}
