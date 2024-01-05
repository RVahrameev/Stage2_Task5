package vtb.courses.stage2.Stage2_Task5.Service;

import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vtb.courses.stage2.Stage2_Task5.Entity.AccountPoolEntity;
import vtb.courses.stage2.Stage2_Task5.Entity.TppProductEntity;
import vtb.courses.stage2.Stage2_Task5.Entity.TppProductRegisterEntity;
import vtb.courses.stage2.Stage2_Task5.Entity.TppRefProductRegisterTypeEntity;
import vtb.courses.stage2.Stage2_Task5.Repository.AccountPoolRepo;
import vtb.courses.stage2.Stage2_Task5.Repository.ProductRegisterTypeRepo;
import vtb.courses.stage2.Stage2_Task5.Repository.ProductRepo;
import vtb.courses.stage2.Stage2_Task5.Request.CreateAccountRequest;
import vtb.courses.stage2.Stage2_Task5.Repository.ProductRegisterRepo;
import vtb.courses.stage2.Stage2_Task5.Response.CreateAccountResponse;

import java.util.List;

@Component
public class CreateAccountService {

    private static ProductRegisterRepo registerRepo;
    private static ProductRepo productRepo;
    private static ProductRegisterTypeRepo registerTypeRepo;

    public CreateAccountResponse createAccount(CreateAccountRequest accountRequest){
        CreateAccountResponse accountResponse = new CreateAccountResponse();

        // Ищем экземпляр продукта по переданному Id
        TppProductEntity productId = productRepo.getReferenceById(accountRequest.getInstanceId());
        if (productId == null) {
            throw new NoResultException("По instanceId \"Идентификатор ЭП\" <"+accountRequest.getInstanceId()+"> не найден экземпляр продукта.");
        }
        // Проверяем на дубли
        TppRefProductRegisterTypeEntity registerTypeEntity = registerTypeRepo.getByValue(accountRequest.getRegistryTypeCode());
        if (registerRepo.existsByProductIdAndRegisterType(accountRequest.getInstanceId(), registerTypeEntity.getId())) {
            throw new IllegalArgumentException("Параметр registryTypeCode \"Тип регистра\" <"+accountRequest.getRegistryTypeCode()+"> уже существует для ЭП с ИД <"+accountRequest.getInstanceId()+">.");
        }

        // Определяем тип регистра
        List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndValue(productId.getProductCodeId().getValue(), accountRequest.getRegistryTypeCode());
        if (registerTypes.isEmpty()) {
            throw new NoResultException("КодПродукта <"+productId.getProductCodeId().getValue()+"> не найдено в Каталоге продуктов для данного типа Регистра \""+accountRequest.getRegistryTypeCode()+"\"");
        }

        // Создаёт продуктовый регистр
        TppProductRegisterEntity accountEntity = new TppProductRegisterEntity(productId, registerTypeEntity, accountRequest.getCurrencyCode(), accountRequest.getBranchCode(), accountRequest.getMdmCode());
        registerRepo.save(accountEntity);

        accountResponse.getData().setAccountId (accountEntity.getId().toString());
        return accountResponse;
    };

    @Autowired
    public void setRegistryTypeRepo(ProductRegisterRepo registerRepo) {
        CreateAccountService.registerRepo = registerRepo;
    }

    @Autowired
    public static void setProductRepo(ProductRepo productRepo) {
        CreateAccountService.productRepo = productRepo;
    }

    @Autowired
    public static void setRegisterTypeRepo(ProductRegisterTypeRepo registerTypeRepo) {
        CreateAccountService.registerTypeRepo = registerTypeRepo;
    }
}
