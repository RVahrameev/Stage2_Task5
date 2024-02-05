package vtb.courses.stage2_task5;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.List;

import org.springframework.test.context.ActiveProfiles;
import vtb.courses.stage2_task5.Controller.AccountController;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesRegex;
import static vtb.courses.stage2_task5.Request.JsonUtil.validateAndParseJson;

import vtb.courses.stage2_task5.Request.JsonUtil;


@SpringBootTest
@ActiveProfiles("test")
class Stage2Task5ApplicationTests {
	@Autowired
	private CsiController csiController;
	@Autowired
	private AccountController accountController;

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

	@Test
	@DisplayName("Проверка контролируемости обязательных параметров")
	public void jsonRequirableTest() throws IOException {
		System.out.println("Проверка контролируемости обязательных параметров");
		String wrongJson = JsonUtil.fileToString("json/csiRequest_MissedRequired.json");
		System.out.println(wrongJson);
		ResponseEntity<CsiResponse> csiResponse = csiController.createCsi(wrongJson);
		System.out.println(csiResponse);
		assertThat("Не произошла проверка на обязательность параметров", csiResponse.toString().replace('\n',' '), matchesRegex(".+Ошибки разбора входящего json.+отсутствует, но требуется.+"));
	}


	@Test
	@DisplayName("Интеграционный тест сервиса создания ЭП, ПР и доп.соглашения")
	public void csiServiceTest() throws IOException {
		System.out.println("Интеграционный тест сервиса создания ЭП, ПР и доп.соглашения");
		// Получим тестовый json
		String json = JsonUtil.fileToString("json/csiRequest.json");
		System.out.println(json);

		// Распарсим его
		CreateCsiRequest csiRequest = validateAndParseJson(json, CreateCsiRequest.getJsonSchema(), CreateCsiRequest.class);

		// Вызовем с ним соответствующий метод контроллера
		ResponseEntity<CsiResponse> csiResponse = csiController.createCsi(json);

		// Проверим что контроллер вернул ЭП
		Integer productId = csiResponse.getBody().getData().getInstanceId();
		Assertions.assertNotNull(productId, "1 Экземпляр продукта не создан");

		// Проверим что ЭП появился в БД
		TppProductEntity productEntity;
		if (productRepo.existsById(productId)) {
			productEntity = productRepo.getReferenceById(productId);
		} else {
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

		// Загрузим тестовый json на создание ПР
		String accountJson = JsonUtil.fileToString("json/createAccountRequest.json");

		// Распарсим его
		CreateAccountRequest accountRequest = validateAndParseJson(accountJson, CreateAccountRequest.getJsonSchema(), CreateAccountRequest.class);
		ResponseEntity<CreateAccountResponse> accountResponse;

		// Для начала пытаемся ещё раз создать такой же счёт,для чего вызовем метод контроллера по обработке json на создание ПР
		accountResponse = accountController.createAccount(accountJson);
		assertThat("Не произошла проверка на существование такого же ПР", accountResponse.toString().replace('\n',' '), matchesRegex(".+Параметр registryTypeCode.+уже существует для ЭП с ИД.+"));
		assertThat("Не сгенерилось исключение 400 BAD_REQUEST", accountResponse.toString().replace('\n',' '), matchesRegex(".+400 BAD_REQUEST.+"));

		// Теперь удалим ПР чтобы можно было его создать заново через Сервис
		productRegisterRepo.delete(productRegisterRepo.getByProductIdAndRegisterType(productEntity,registerTypeEntity));

		// Проверим что удалился
		Assertions.assertFalse(productRegisterRepo.existsByProductIdAndRegisterType(productEntity,registerTypeEntity),"Не удалось удалить созданный ПР");

		// Для начала вызовем его с неверным продуктом, чтобы проверить генерацию исключения
		accountResponse = accountController.createAccount(accountJson.replaceAll("\"instanceId\": \\d+,", "\"instanceId\": 777,"));
		assertThat("Не произошла проверка на существование продукта", accountResponse.toString().replace('\n',' '), matchesRegex(".+По instanceId.+не найден экземпляр продукта.+"));
		assertThat("Не сгенерилось исключение 404 NOT_FOUND", accountResponse.toString().replace('\n',' '), matchesRegex(".+404 NOT_FOUND.+"));

		// Вызовем метод контроллера по обработке json на создание ПР
		accountResponse = accountController.createAccount(accountJson);

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
		String jsonAgreement = JsonUtil.fileToString("json/csiRequestAddAgreement.json");

		// Распарсим его
		CreateCsiRequest csiAgreementRequest = validateAndParseJson(jsonAgreement, CreateCsiRequest.getJsonSchema(), CreateCsiRequest.class);

		// Вызовем с ним соответствующий метод контроллера
		ResponseEntity<CsiResponse> csiAgreementResponse = csiController.createCsi(jsonAgreement);

		// Проверим что контроллер вернул ЭП
		Integer productIdAgreement = csiAgreementResponse.getBody().getData().getInstanceId();
		Assertions.assertNotNull(productIdAgreement, "14 Экземпляр продукта для добаления соглашения не не найден");

		// Проверим что соглашение записалось в БД
		List<AgreementsEntity> agreements = agreementsRepo.findAll();
		Assertions.assertFalse(agreements.isEmpty(), "15 Доп.соглашения не созданы!");
		for (AgreementsEntity agreement: agreements) {
			// Проверим номер доп.соглашения
			Assertions.assertEquals(csiAgreementRequest.getInstanceAgreement()[0].getNumber(), agreement.getNumber(), "16 Доп.соглашение создано с неверным номером");
		}
	}

	@Test
	@DisplayName("Тест возникновения ошибок при работе сервиса создания ЭП")
	public void csiServiceErrorTest() throws IOException {
		System.out.println("Тест возникновения ошибок при работе сервиса создания ЭП");
		// Получим тестовый json
		String json = JsonUtil.fileToString("json/csiRequestNoProduct.json");
		System.out.println(json);
		// Вызовем с ним соответствующий метод контроллера
		ResponseEntity<CsiResponse> csiResponse = csiController.createCsi(json);
		System.out.println(csiResponse);
		assertThat("Не сгенерилось исключение по не найденному продукту", csiResponse.toString().replace('\n',' '), matchesRegex(".+КодПродукта.+не найден в Каталоге продуктов.+"));
		assertThat("Не сгенерилось исключение 404 NOT_FOUND", csiResponse.toString().replace('\n',' '), matchesRegex(".+404 NOT_FOUND.+"));
	}

}

