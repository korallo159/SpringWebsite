package com.koral.webKoral;

import com.koral.webKoral.chat.ChatMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class WebKoralApplication {


	public static void main(String[] args) {


		SpringApplication.run(WebKoralApplication.class, args);

	}
	@Bean
	UnicastProcessor<ChatMessage> publisher(){
		return UnicastProcessor.create();
	}

	@Bean
	Flux<ChatMessage> messages(UnicastProcessor<ChatMessage> publisher) {
		return publisher.replay(30).autoConnect();
	}

}
