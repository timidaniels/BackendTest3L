package com.adedoyin.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.adedoyin.backend.question2.interfaces.CardSchemeRepository;
import com.adedoyin.backend.question2.models.CardScheme;


@SpringBootApplication
public class BackendTestApplication {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(BackendTestApplication.class, args);
	}	
	
	@Bean
	ApplicationRunner applicationRunner(CardSchemeRepository cardSchemeRepository) {
		logger.info("DB SEEDING SAVING STARTED");
		return args -> {
			String[] banks = { "UBS", "FCMD", "" };
			String[] schemes = { "visa", "mastercard", "amex" };
			String[] cards = { "4166676667666746", "4646464646464644", "5136333333333335", "4017340000000003",
					"5585558555855583", "5555444433331111", "2222410740360010", "5555555555554444", "2222410700000002",
					"2222400010000008", "2223000048410010", "2222400060000007", "2223520443560010", "5500000000000004",
					"6771798025000004", "5100705000000002", "5106040000000008", "4000620000000007", "4000060000000006",
					"4000180000000002" };

			for (int i = 0; i < 20; i++) {
				int rand = (int) Math.round(Math.random()*2);
				
				cardSchemeRepository.save(new CardScheme(cards[i], schemes[rand], "debit",
						banks[rand], 0));
			}
		};

	}
}
