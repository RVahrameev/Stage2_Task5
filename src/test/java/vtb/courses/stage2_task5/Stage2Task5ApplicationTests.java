package vtb.courses.stage2_task5;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.test.context.ActiveProfiles;
import vtb.courses.stage2_task5.Controller.CsiController;
import vtb.courses.stage2_task5.Entity.AgreementsEntity;
import vtb.courses.stage2_task5.Entity.TppProductEntity;
import vtb.courses.stage2_task5.Entity.TppProductRegisterEntity;
import vtb.courses.stage2_task5.Entity.TppRefProductRegisterTypeEntity;
import vtb.courses.stage2_task5.Repository.AgreementsRepo;
import vtb.courses.stage2_task5.Repository.ProductRegisterRepo;
import vtb.courses.stage2_task5.Repository.ProductRegisterTypeRepo;
import vtb.courses.stage2_task5.Repository.ProductRepo;
import vtb.courses.stage2_task5.Request.CreateAccountRequest;
import vtb.courses.stage2_task5.Request.CreateCsiRequest;
import vtb.courses.stage2_task5.Response.CreateAccountResponse;
import vtb.courses.stage2_task5.Response.CsiResponse;
import vtb.courses.stage2_task5.Service.CreateAccountService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesRegex;

@SpringBootTest
//@AutoConfigureMockWebServiceClient
//@FlywayTest(locationsForMigrate = {"scripts"}, overrideLocations = true)
@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {Stage2Task5ApplicationTests.TestConfig.class})
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
//		FlywayTestExecutionListener.class })
class Stage2Task5ApplicationTests {
	@Autowired
	private CreateAccountService createAccountService;
	@Autowired
	private CsiController csiController;

	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private ProductRegisterRepo productRegisterRepo;
	@Autowired
	private ProductRegisterTypeRepo productRegisterTypeRepo;
	@Autowired
	private AgreementsRepo agreementsRepo;

	@BeforeEach
	void clearDatabase(@Autowired Flyway flyway) {
		flyway.clean();
		flyway.migrate();
	}

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
		String wrongJson = fileToString("json/csiRequest_MissedRequired.json");
		ResponseEntity<CsiResponse> csiResponse = csiController.createCsi(wrongJson);
		assertThat("Не произошла проверка на обязательность параметров", csiResponse.toString().replace('\n',' '), matchesRegex(".+Ошибки разбора входящего json.+отсутствует, но требуется.+"));
	}


	@Test
	@DisplayName("Интеграционный тест сервиса создания ЭП, ПР и доп.соглашения")
	public void csiServiceTest() throws IOException {
		// Получим тестовый json
		String json = fileToString("json/csiRequest.json");
		// Распарсим его
		CreateCsiRequest csiRequest = csiController.validateAndParseJson(json, CreateCsiRequest.getJsonSchema(), CreateCsiRequest.class);
		// Вызовем с ним соответствующий метод контроллера
		ResponseEntity<CsiResponse> csiResponse = csiController.createCsi(json);
		// Проверим что контроллер вернул ЭП
		Integer productId = csiResponse.getBody().getData().getInstanceId();
		Assertions.assertNotNull(productId, "1 Экземпляр продукта не создан");
		// Проверим что ЭП появился в БД
		TppProductEntity productEntity;
		try {
			productEntity = productRepo.getById(productId);;
		} catch (Exception e) {
			throw new AssertionError("2 Продукт не сохранён в базу данных");
		}
		// Проверяем основные параметры продукта, которые записались в базу данных
		Assertions.assertEquals(csiRequest.getProductCode(), productEntity.getProductCodeId().getValue(), "3 Не верно сохранился ProductCode");
		Assertions.assertEquals(csiRequest.getMdmCode(), productEntity.getClientId().getMdmCode(), "4 Не верно сохранился MdmCode");
		Assertions.assertEquals(csiRequest.getContractNumber(), productEntity.getNumber(), "5 Не верно сохранился ContractNumber");
		Assertions.assertEquals(csiRequest.getProductType(), productEntity.getType().name(), "6 Не верно сохранился ProductType");
		Assertions.assertEquals(csiRequest.getIsoCurrencyCode(), productEntity.getCurrency(), "7 Не верно сохранился IsoCurrencyCode");
		TppRefProductRegisterTypeEntity registerTypeEntity;
		try {
			registerTypeEntity = productRegisterTypeRepo.getByValue(csiRequest.getRegisterType());
		} catch (Exception e) {
			throw new AssertionError("8 Не найден тип регистра "+csiRequest.getRegisterType());
		}
		Assertions.assertTrue(productRegisterRepo.existsByProductIdAndRegisterType(productEntity,registerTypeEntity),"Не создан продуктовый регистр нужного типа");

		// Теперь удалим ПР и создадим его заново через Сервис
		productRegisterRepo.delete(productRegisterRepo.getByProductIdAndRegisterType(productEntity,registerTypeEntity));
		// Проверим что удалился
		Assertions.assertFalse(productRegisterRepo.existsByProductIdAndRegisterType(productEntity,registerTypeEntity),"Не удалось удалить созданный ПР");
		// Загрузим тестовый json на создание ПР
		String accountJson = fileToString("json/createAccountRequest.json");
		// Распарсим его
		CreateAccountRequest accountRequest = csiController.validateAndParseJson(accountJson, CreateAccountRequest.getJsonSchema(), CreateAccountRequest.class);
		// Для начала вызовем его с неверным продуктом, чтобы проверить генерацию исключения
		ResponseEntity<CreateAccountResponse> accountResponse;
		System.out.println(accountJson.replaceAll("\"instanceId\": \\d+,", "\"instanceId\": 777,"));
		accountResponse = csiController.createAccount(accountJson.replaceAll("\"instanceId\": \\d+,", "\"instanceId\": 777,"));
		assertThat("Не произошла проверка на существование продукта", accountResponse.toString().replace('\n',' '), matchesRegex(".+По instanceId.+не найден экземпляр продукта.+"));
		// Вызовем метод контроллера по обработке json на создание ПР
		accountResponse = csiController.createAccount(accountJson);
		// Проверим что контроллер вернул ПР
		Assertions.assertNotNull(accountResponse.getBody().getData().getAccountId(), "9 Экземпляр продукта не создан");
		try {
			registerTypeEntity = productRegisterTypeRepo.getByValue(accountRequest.getRegistryTypeCode());
		} catch (Exception e) {
			throw new AssertionError("10 Не найден тип регистра ПР "+accountRequest.getRegistryTypeCode());
		}
		// Проверим что ПР записался в БД
		Assertions.assertTrue(productRegisterRepo.existsByProductIdAndRegisterType(productEntity,registerTypeEntity),"11 Не создан продуктовый регистр нужного типа");
		TppProductRegisterEntity registerEntity = productRegisterRepo.getByProductIdAndRegisterType(productEntity,registerTypeEntity);
		// Проверим некторорые параметры созданного ПР
		Assertions.assertEquals(accountRequest.getAccountType(), registerEntity.getRegisterType().getAccountType(), "12 ПР создан с неверным AccountType");
		Assertions.assertEquals(accountRequest.getCurrencyCode(), registerEntity.getCurrency(), "13 ПР создан с неверным CurrencyCode");

		// Теперь к созданному ЭП создаём доп.соглашение
		// Получим тестовый json
		String jsonAgreement = fileToString("json/csiRequestAddAgreement.json");
		// Распарсим его
		CreateCsiRequest csiAgreementRequest = csiController.validateAndParseJson(jsonAgreement, CreateCsiRequest.getJsonSchema(), CreateCsiRequest.class);
		// Вызовем с ним соответствующий метод контроллера
		ResponseEntity<CsiResponse> csiAgreementResponse = csiController.createCsi(jsonAgreement);
		// Проверим что контроллер вернул ЭП
		Integer productIdAgreement = csiAgreementResponse.getBody().getData().getInstanceId();
		Assertions.assertNotNull(productId, "14 Экземпляр продукта для добаления соглашения не не найден");
		// Проверим что соглашение записалось в БД
		List<AgreementsEntity> agreements = agreementsRepo.findAll();
		Assertions.assertTrue(!agreements.isEmpty(), "15 Доп.соглашения не созданы!");
		for (AgreementsEntity agreement: agreements) {
			// Проверим номер доп.соглашения
			Assertions.assertEquals(csiAgreementRequest.getInstanceAgreement()[0].getNumber(), agreement.getNumber(), "16 Доп.соглашение создано с неверным номером");
		}

		// Проверка на отлов ошибки добавления доп.соглашения с тем же номером
		csiAgreementResponse = csiController.createCsi(jsonAgreement);
		assertThat("17 Не произошла проверка на повтор номера доп.соглашения", csiAgreementResponse.toString().replace('\n',' '), matchesRegex(".+Параметр Number.+Дополнительного соглашения (сделки).+"));
	}

	@Test
	@DisplayName("Тест возникновения ошибок при работе сервиса создания ЭП")
	public void csiServiceErrorTest() throws IOException {
		// Получим тестовый json
		String json = fileToString("json/csiRequestNoProduct.json");
		// Распарсим его
		CreateCsiRequest csiRequest = csiController.validateAndParseJson(json, CreateCsiRequest.getJsonSchema(), CreateCsiRequest.class);
		// Вызовем с ним соответствующий метод контроллера
		ResponseEntity<CsiResponse> csiResponse = csiController.createCsi(json);
		System.out.println(csiResponse);
		assertThat("Не сгенерилось исключение по не найденному продукту", csiResponse.toString().replace('\n',' '), matchesRegex(".+КодПродукта.+не найден в Каталоге продуктов.+"));
		assertThat("Не сгенерилось исключение 404 NOT_FOUND", csiResponse.toString().replace('\n',' '), matchesRegex(".+404 NOT_FOUND.+"));
	}

}

