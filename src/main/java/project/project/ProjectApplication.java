package project.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import project.project.aop.trace.LogTrace;
import project.project.config.AopConfig;
import project.project.config.InterceptorConfig;

@SpringBootApplication
@EnableJpaAuditing
@Import({AopConfig.class, InterceptorConfig.class})
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}


	@Bean
	public LogTrace logTrace(){
		return new LogTrace();
	}
}
