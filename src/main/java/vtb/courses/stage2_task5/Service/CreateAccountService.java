package vtb.courses.stage2_task5.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vtb.courses.stage2_task5.Entity.TppProductEntity;
import vtb.courses.stage2_task5.Entity.TppProductRegisterEntity;
import vtb.courses.stage2_task5.Entity.TppRefProductRegisterTypeEntity;
import vtb.courses.stage2_task5.Repository.ProductRegisterTypeRepo;
import vtb.courses.stage2_task5.Repository.ProductRepo;
import vtb.courses.stage2_task5.Request.CreateAccountRequest;
import vtb.courses.stage2_task5.Repository.ProductRegisterRepo;
import vtb.courses.stage2_task5.Response.CreateAccountResponse;

import java.util.List;

@Service
public class CreateAccountService {

    private ProductRegisterRepo registerRepo;
    private ProductRepo productRepo;
    private ProductRegisterTypeRepo registerTypeRepo;
    private AccountNumService accountNumService;


    @Transactional
    public CreateAccountResponse createAccount(CreateAccountRequest accountRequest){
        TppProductEntity productId;
        CreateAccountResponse accountResponse = new CreateAccountResponse();

        // Ищем экземпляр продукта по переданному Id
        try {
            productId = productRepo.getReferenceById(accountRequest.getInstanceId());
        } catch (EntityNotFoundException e) {
            throw new NoResultException("По instanceId \"Идентификатор ЭП\" <"+accountRequest.getInstanceId()+"> не найден экземпляр продукта.");
        }
        // Проверяем на дубли
        TppRefProductRegisterTypeEntity registerTypeEntity = registerTypeRepo.getByValue(accountRequest.getRegistryTypeCode());
        if (registerRepo.existsByProductIdAndRegisterType(productId, registerTypeEntity)) {
            throw new IllegalArgumentException("Параметр registryTypeCode \"Тип регистра\" <"+accountRequest.getRegistryTypeCode()+"> уже существует для ЭП с ИД <"+accountRequest.getInstanceId()+">.");
        }

        // Определяем тип регистра
        List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndValue(productId.getProductCodeId().getValue(), accountRequest.getRegistryTypeCode());
        if (registerTypes.isEmpty()) {
            throw new NoResultException("КодПродукта <"+productId.getProductCodeId().getValue()+"> не найдено в Каталоге продуктов для данного типа Регистра \""+accountRequest.getRegistryTypeCode()+"\"");
        }

        // Получаем номер счёта
        String accountNum = accountNumService.getAccountNum(
                accountRequest.getBranchCode(),
                accountRequest.getCurrencyCode(),
                accountRequest.getMdmCode(),
                registerTypeEntity
        );

        // Создаём ПР
        TppProductRegisterEntity accountEntity = new TppProductRegisterEntity(productId, registerTypeEntity, accountNum, accountRequest.getCurrencyCode());

        registerRepo.save(accountEntity);

        accountResponse.getData().setAccountId (accountEntity.getId().toString());
        return accountResponse;
    }

    @Autowired
    public void setRegistryTypeRepo(ProductRegisterRepo registerRepo) {
        this.registerRepo = registerRepo;
    }

    @Autowired
    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Autowired
    public void setRegisterTypeRepo(ProductRegisterTypeRepo registerTypeRepo) {
        this.registerTypeRepo = registerTypeRepo;
    }

    @Autowired
    public void setAccountNumService(AccountNumService accountNumService) {
        this.accountNumService = accountNumService;
    }
}
