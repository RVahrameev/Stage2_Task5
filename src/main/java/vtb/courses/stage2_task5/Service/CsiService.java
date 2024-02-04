package vtb.courses.stage2_task5.Service;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vtb.courses.stage2_task5.Entity.*;
import vtb.courses.stage2_task5.Repository.*;
import vtb.courses.stage2_task5.Request.CreateCsiRequest;
import vtb.courses.stage2_task5.Response.CsiResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CsiService implements CsiServiceIntf{

    private ProductRepo productRepo;
    private ProductRegisterTypeRepo registerTypeRepo;
    private ProductRegisterRepo registerRepo;
    private AccountNumServiceIntf accountNumService;
    private AgreementsRepo agreementsRepo;
    private ProductClassRepo productClassRepo;
    private ClientRepo clientRepo;
    private BranchRepo branchRepo;

    private TppProductEntity createProductEntity(CreateCsiRequest csiRequest) {
        TppProductEntity pe = new TppProductEntity();
        pe.setProductCodeId(productClassRepo.getByValue(csiRequest.getProductCode()));
        pe.setClientId(clientRepo.getByMdmCode(csiRequest.getMdmCode()));
        pe.setType(ProductType.valueOf(csiRequest.getProductType()));
        pe.setNumber(csiRequest.getContractNumber());
        pe.setPriority(csiRequest.getPriority());
        pe.setDateOfConclusion(csiRequest.getContractDate());
        pe.setStartDateTime(new Date());
        pe.setPenaltyRate(BigDecimal.valueOf(csiRequest.getInterestRatePenalty()));
        pe.setNso(BigDecimal.valueOf(csiRequest.getMinimalBalance()));
        pe.setThresholdAmount(BigDecimal.valueOf(csiRequest.getThresholdAmount()));
        pe.setInterestRateType(csiRequest.getRateType());
        pe.setTaxRate(BigDecimal.valueOf(csiRequest.getTaxPercentageRate()));
        pe.setState("OPEN");
        pe.setCurrency(csiRequest.getIsoCurrencyCode());
        pe.setBranch(branchRepo.getByCode(csiRequest.getBranchCode()));
        return pe;
    }

    @Transactional
    public CsiResponse createCsi(CreateCsiRequest csiRequest){
        CsiResponse csiResponse = new CsiResponse();
        TppProductEntity productEntity;
        Integer productId = csiRequest.getInstanceId();

        if (productId == null) {

            // Проверяем корректность переданного значения в поле ProductCode
            List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndAccountType(csiRequest.getProductCode(), "Клиентский");
            if (registerTypes.isEmpty()) {
                throw new NoResultException("КодПродукта =\""+csiRequest.getProductCode()+"\" не найден в Каталоге продуктов (tpp_ref_product_register_type)");
            }

            // Проверяем что нет договора с таким же номером
            TppProductEntity existProduct = productRepo.getByNumber(csiRequest.getContractNumber());
            if (existProduct != null) {
                throw new IllegalArgumentException("Параметр ContractNumber \"№ договора\" "+csiRequest.getContractNumber()+" уже существует для \n ЭП с ИД "+existProduct.getId());
            }

            // Создаем ЭП
            productEntity = createProductEntity(csiRequest);

            // Создаём связанные ПР
            for (TppRefProductRegisterTypeEntity registerType: registerTypes) {
                // Получаем номер счёта
                String accountNum = accountNumService.getAccountNum(csiRequest.getBranchCode(), csiRequest.getIsoCurrencyCode(), csiRequest.getMdmCode(), registerType);
                TppProductRegisterEntity prEntity = new TppProductRegisterEntity(productEntity, registerType, accountNum, csiRequest.getIsoCurrencyCode());
                registerRepo.save(prEntity);
                // Созданные счета добавляем в ответ
                csiResponse.getData().getRegisterId().add(prEntity.getId());
            }

        } else {
            Optional<TppProductEntity> product = productRepo.findById(productId);
            // Проверяем что нашли продукт
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Не найден договор соответствующий параметру instanceId \"Идентификатор экземпляра продукта\" = "+csiRequest.getInstanceId());
            }
            productEntity = product.get();

            // Проверяем что нет совпадений по номерам доп.соглашений
            for (CreateCsiRequest.Agreement agreement: csiRequest.getInstanceAgreement()) {
                Iterator<AgreementsEntity> agreements = productEntity.getAgreements();
                while (agreements.hasNext()) {
                    AgreementsEntity agreementEntity = agreements.next();
                    if (agreementEntity.getNumber().equals(agreement.getNumber())) {
                        throw new IllegalArgumentException(" Параметр Number \"№ Дополнительного соглашения (сделки)\" = \""+agreement.getNumber()+"\" уже существует для ЭП с ИД "+productId);
                    }
                }
                // Добавляем новое доп.соглашение
                AgreementsEntity agreementsEntity = new AgreementsEntity(agreement.getNumber());
                productEntity.addAgreement(agreementsEntity);
                agreementsRepo.save(agreementsEntity);
                // Созданные доп.соглашения добавляем в ответ
                csiResponse.getData().getSupplementaryAgreementId().add(agreementsEntity.getId());
            }

        }

        productRepo.saveAndFlush(productEntity);

        // Дозаполняем ответ
        csiResponse.getData().setInstanceId(productEntity.getId());

        return csiResponse;
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
    public void setRegistryTypeRepo(ProductRegisterRepo registerRepo) {
        this.registerRepo = registerRepo;
    }
    @Autowired
    public void setAccountNumService(AccountNumServiceIntf accountNumService) {
        this.accountNumService = accountNumService;
    }
    @Autowired
    public void setAgreementsRepo(AgreementsRepo agreementsRepo) {
        this.agreementsRepo = agreementsRepo;
    }
    @Autowired
    public void setRegistryTypeRepo(ProductClassRepo productClassRepo) {
        this.productClassRepo = productClassRepo;
    }
    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }
    @Autowired
    public void setBranchRepo(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

}
