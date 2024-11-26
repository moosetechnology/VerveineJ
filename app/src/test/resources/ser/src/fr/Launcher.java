package fr;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.rest.JsonBodyArgumentResolver;

@SpringBootApplication
public class Launcher implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new JsonBodyArgumentResolver());
	}
}
