package vtb.courses.stage2_task5.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import vtb.courses.stage2_task5.Entity.AccountPoolEntity;
import vtb.courses.stage2_task5.Entity.TppRefProductRegisterTypeEntity;
import vtb.courses.stage2_task5.Repository.AccountPoolRepo;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @MockBean
    private AccountPoolRepo poolRepo;

    @InjectMocks
    private AccountNumService accountNumService;
    @Test
    @DisplayName("Тест работы пула счетов")
    public void accountNumServiceTest(){
        // Формируем тестовый пул счетов
        AccountPoolEntity accountPool = new AccountPoolEntity();
        accountPool.setBranchCode("001");
        accountPool.setMdmCode("cl001");
        accountPool.setCurrencyCode("A98");
        accountPool.setRegisterTypeCode("03.012.002_47533_ComSoLd");
        List<String> accounts = new ArrayList<>();
        accounts.add("475335516415314841861");
        accounts.add("4753321651354151");
        accounts.add("4753352543276345");
        accountPool.setAccounts(accounts);

        // Тестовый экзеплят типа продукта
        TppRefProductRegisterTypeEntity typeEntity = new TppRefProductRegisterTypeEntity();
        typeEntity.setValue("03.012.002_47533_ComSoLd");

        // Моккируем вызов репозитория, возвращая ранее созданный пул счетов
        given(poolRepo.getByBranchCodeAndCurrencyCodeAndMdmCodeAndRegisterTypeCode("001", "A98", "cl001", "03.012.002_47533_ComSoLd"))
                .willReturn(accountPool);

        // Проводим операции по извлечению счетов из пула, и проверку exception когда пул закончится
        Assertions.assertEquals("475335516415314841861", accountNumService.getAccountNum("001", "A98", "cl001", typeEntity), "Из пула счетов вернули не тот номер счёта");
        Assertions.assertEquals(2, accountPool.getAccounts().size(), "Из пула счетов не удалён использованный счёт");
        Assertions.assertEquals("4753321651354151", accountNumService.getAccountNum("001", "A98", "cl001", typeEntity), "Из пула счетов вернули не тот номер счёта");
        Assertions.assertEquals(1, accountPool.getAccounts().size(), "Из пула счетов не удалён использованный счёт");
        Assertions.assertEquals("4753352543276345", accountNumService.getAccountNum("001", "A98", "cl001", typeEntity), "Из пула счетов вернули не тот номер счёта");
        Assertions.assertEquals(0, accountPool.getAccounts().size(), "Из пула счетов не удалён использованный счёт");
        Assertions.assertThrows(NoResultException.class, ()->accountNumService.getAccountNum("001", "A98", "cl001", typeEntity), "Не сгенерировалась ошибка при отсутствии счёта в пуле");
    }
}
