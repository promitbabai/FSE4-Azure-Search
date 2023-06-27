package com.iiht.fse4.skilltrackersearch;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.support.converter.StringJsonMessageConverter;
/*import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/




@SpringBootApplication
//@EnableEurekaClient
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Skill-Tracker Search API", version = "1.0", description = "Search the Associates based on Skills"))
public class SkillTrackerSearchApplication {



	public static void main(String[] args) {
		SpringApplication.run(SkillTrackerSearchApplication.class, args);
	}



//
//	@Bean
//	public StringJsonMessageConverter jsonConverter() {
//		return new StringJsonMessageConverter();
//	}
//


}

