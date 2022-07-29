package fivesenses.server.fivesenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(
		exclude = {
				org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
				org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
				org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration.class
		}
)
public class FivesensesApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.location="

//			+ "classpath:application.yml,"
//			+ "classpath:application-local.yml,"

			+ "classpath:application-prod.yml";


	public static void main(String[] args){
		new SpringApplicationBuilder(FivesensesApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}
}
