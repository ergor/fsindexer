package no.netb.magnetar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@SpringBootApplication
public class MagnetarApplication {

	public static final String WEBUI_PATH = "/webui";
	public static final String API_PATH = "/api";

	@Autowired
	Environment env;

	public static void main(String[] args) {
		SpringApplication.run(MagnetarApplication.class, args);
	}

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db.driver")));
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		return dataSource;
	}

	public static String webuiPath(String requestPath) {
		return WEBUI_PATH + requestPath;
	}
	public static String apiPath(String requestPath) {
		return API_PATH + requestPath;
	}
}
