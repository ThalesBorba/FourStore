package br.com.fourcamp.fourstore;

import br.com.fourcamp.fourstore.util.DotenvUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FourStoreApplication {

	public static void main(String[] args) {

		DotenvUtil.loadDenv();
		SpringApplication.run(FourStoreApplication.class, args);
	}

}
