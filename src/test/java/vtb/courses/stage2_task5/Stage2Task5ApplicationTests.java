package vtb.courses.stage2_task5;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.dbcp.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.hamcrest.core.StringRegularExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import io.restassured.RestAssured.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesRegex;

@SpringBootTest
@FlywayTest(locationsForMigrate = {"scripts"}, overrideLocations = true)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Stage2Task5ApplicationTests.TestConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
		FlywayTestExecutionListener.class })
class Stage2Task5ApplicationTests {

	private String fileToString(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream(fileName)));
		String line;
		StringBuilder text = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			text.append(line);
		}
		reader.close();

		return text.toString();
	}

	@Test
	@DisplayName("Проверка контролируемости обязательных параметров")
	public void jsonReqiurableTest() throws IOException {
		String wrongJson = fileToString("csiRequest_MissedRequired.json");
		//String wrongJson = fileToString("csiRequest.json");
		System.out.println("wrongJson = "+wrongJson);
		RestAssured.baseURI = "http://localhost:8080";
		String response = given()
				.contentType(ContentType.JSON)
				.body(wrongJson)
				.post("/corporate-settlement-instance/create/")
				.then()
				.statusCode(400)
				.extract()
				.response()
				.print();
		assertThat("Не произошла проверка на обязательность параметров", response, matchesRegex(".+Ошибки разбора входящего json.+отсутствует, но требуется.+"));
	}


	@Test
	@DisplayName("Тест сервиса создания ЭП")
	public void csiServiceTest() throws IOException {
		String json = fileToString("csiRequest.json");
		//String wrongJson = fileToString("csiRequest.json");
		System.out.println("srcJson = "+json);
		RestAssured.baseURI = "http://localhost:8080";
		String response = given()
				.contentType(ContentType.JSON)
				.body(json)
				.post("/corporate-settlement-instance/create/")
				.print();
//				.then()
//				.statusCode(200)
//				.extract()
//				.response()
//				.print();
		System.out.println("response = "+response);
		//assertThat("Не произошла проверка на обязательность параметров", response, matchesRegex(".+Ошибки разбора входящего json.+отсутствует, но требуется.+"));
	}

	@Configuration
//	@PropertySources({
//			@PropertySource(name = "flywayprop", value = "classpath:flyway.properties"),
//			@PropertySource(name = "jdbcprop", value = "classpath:jdbc_h2.properties")
//	})
	public static class TestConfig {
//
//		@Value("${flyway.locations}")
//		public String flywayLocations;
//
//		public String flywaySecondLocation = "/secondLocation";
//
//		@Value("${jdbc.driver}")
//		public String driverClassName;
//		@Value("${jdbc.url}")
//		public String url;
//		@Value("${jdbc.username}")
//		public String username;
//		@Value("${jdbc.password}")
//		public String password;

		/**
		 * First flyway configuration use "flyway.locations" from properties file.
		 */
		@Bean(name = "flywayOne")
		public Flyway flywayOne(DataSource dataSource) {
			return Flyway.configure()
					.cleanDisabled(false)
					.dataSource(dataSource)
					.locations("scripts")
					.load();
		}

		/**
		 * Second flyway configuration use "flyway.locations" hard coded value.
		 */
//		@Bean(name = "flywaySecond")
//		public Flyway flywaySecond(DataSource dataSource) {
//			return Flyway.configure()
//					.dataSource(dataSource)
//					.locations(flywaySecondLocation)
//					.load();
//		}
//

		/**
		 * Datasource configuration.
		 * Alias name only needed for test helper.
		 */
		@Bean(name = {"dataSource", "dataSourceRef"})
		public DataSource dataSource() {
			BasicDataSource dataSource = new BasicDataSource();

			dataSource.setMaxActive(-1);

			dataSource.setUsername("postgres");

			dataSource.setPassword("sys");
			dataSource.setUrl("jdbc:postgresql://localhost:5433/postgres");
			dataSource.setDriverClassName("org.postgresql.Driver");

			return dataSource;
		}

		/*
		 * Needed to get properties correct loaded.
		 */
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
			return new PropertySourcesPlaceholderConfigurer();
		}

	}
}

