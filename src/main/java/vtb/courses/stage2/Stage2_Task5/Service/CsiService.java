package vtb.courses.stage2.Stage2_Task5.Service;

import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import vtb.courses.stage2.Stage2_Task5.Entity.TppProductEntity;
import vtb.courses.stage2.Stage2_Task5.Entity.TppRefProductRegisterTypeEntity;
import vtb.courses.stage2.Stage2_Task5.Repository.AgreementsRepo;
import vtb.courses.stage2.Stage2_Task5.Repository.ProductRegistryTypeRepo;
import vtb.courses.stage2.Stage2_Task5.Repository.ProductRepo;
import vtb.courses.stage2.Stage2_Task5.Request.CreateCsiRequest;
import vtb.courses.stage2.Stage2_Task5.Response.CsiResponse;

import java.util.List;
import java.util.Optional;

public class CsiService {

    private ProductRepo productRepo;
    private AgreementsRepo agreementsRepo;
    private ProductRegistryTypeRepo registryTypeRepo;

    public CsiResponse createCsi(CreateCsiRequest csiRequest){
        CsiResponse csiResponse = new CsiResponse();

        Integer productId = csiRequest.getInstanceId();

        // Проверяем корректность переданного значения в поле ProductCode
        List<TppRefProductRegisterTypeEntity> registerTypes = registryTypeRepo.findAllByProductClassCodeAndAccountType(csiRequest.getProductCode(), csiRequest.getRegisterType());
        if (registerTypes.isEmpty()) {
            throw new NoResultException("КодПродукта =\""+csiRequest.getProductCode()+"\" не найден в Каталоге продуктов (tpp_ref_product_register_type)");
        }

        if (productId == null) {
            // Проверяем что нет договора с таким же номером
            if (productRepo.existsByNumber(csiRequest.getContractNumber())) {
                throw new IllegalArgumentException("Параметр ContractNumber \"№ договора\" "+csiRequest.getContractNumber()+" уже существует для \n ЭП с ИД "+productId);
            }

        } else {
            Optional<TppProductEntity> product = productRepo.findById(productId);
            // Проверяем что нашли продукт
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Не найден договор соответствующий параметру instanceId \"Идентификатор экземпляра продукта\" = "+csiRequest.getInstanceId());
            }

            // Проверяем что нет совпадений по номерам доп.соглашений
            for (CreateCsiRequest.Agreement agreement: csiRequest.getInstanceAgreement()) {
                if (agreementsRepo.existsByNumberAndAgreementId(agreement.getNumber(), product.get().getAgreementId())) {
                    throw new IllegalArgumentException(" Параметр Number \"№ Дополнительного соглашения (сделки)\" = \""+agreement.getNumber()+"\" уже существует для ЭП с ИД "+productId);
                }

            }
        }

        csiResponse.getData().setInstanceId(777);
        csiResponse.getData().setRegisterId(new int[]{111,222,333});
        return csiResponse;
    };

    @Autowired
    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Autowired
    public void setAgreementsRepo(AgreementsRepo agreementsRepo) {
        this.agreementsRepo = agreementsRepo;
    }

    @Autowired
    public void setRegistryTypeRepo(ProductRegistryTypeRepo registryTypeRepo) {
        this.registryTypeRepo = registryTypeRepo;
    }

}
