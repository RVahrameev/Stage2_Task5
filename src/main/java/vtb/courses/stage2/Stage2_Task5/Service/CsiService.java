package vtb.courses.stage2.Stage2_Task5.Service;

import org.springframework.beans.factory.annotation.Autowired;
import vtb.courses.stage2.Stage2_Task5.Entity.TppProductEntity;
import vtb.courses.stage2.Stage2_Task5.Repository.AgreementsRepo;
import vtb.courses.stage2.Stage2_Task5.Repository.ProductRegisterTypeRepo;
import vtb.courses.stage2.Stage2_Task5.Repository.ProductRepo;
import vtb.courses.stage2.Stage2_Task5.Request.CreateCsiRequest;
import vtb.courses.stage2.Stage2_Task5.Response.CsiResponse;

public class CsiService {

    private ProductRepo productRepo;
    private AgreementsRepo agreementsRepo;
    private ProductRegisterTypeRepo registryTypeRepo;

    public CsiResponse createCsi(CreateCsiRequest csiRequest){
        CsiResponse csiResponse = new CsiResponse();

        TppProductEntity productEntity = new TppProductEntity(csiRequest);
        productRepo.save(productEntity);

        csiResponse.getData().setInstanceId(productEntity.getId());
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
    public void setRegistryTypeRepo(ProductRegisterTypeRepo registryTypeRepo) {
        this.registryTypeRepo = registryTypeRepo;
    }

}
