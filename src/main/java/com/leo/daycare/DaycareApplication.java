package com.leo.daycare;

import com.leo.daycare.model.CensusData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication
public class DaycareApplication {

	@Configuration
	public class ThymeleafConfig {

		@Bean
		public ClassLoaderTemplateResolver secondaryTemplateResolver() {
			ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
			secondaryTemplateResolver.setPrefix("templates/");
			secondaryTemplateResolver.setSuffix(".html");
			secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
			secondaryTemplateResolver.setCharacterEncoding("UTF-8");
			secondaryTemplateResolver.setOrder(1);
			secondaryTemplateResolver.setCheckExistence(true);

			return secondaryTemplateResolver;
		}
	}

	@Bean
	public CensusData beanCensusData() {
		return new CensusData();
	}

	public static void main(String[] args) {
		SpringApplication.run(DaycareApplication.class, args);
	}

}
