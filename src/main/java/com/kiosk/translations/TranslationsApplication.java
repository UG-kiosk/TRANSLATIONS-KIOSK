package com.kiosk.translations;

import com.kiosk.translations.azure.AzureTranslatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TranslationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslationsApplication.class, args);
	}

}
