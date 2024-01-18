package sion.test.shoppingbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class ShoppingBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingBatchApplication.class, args);
	}

}
