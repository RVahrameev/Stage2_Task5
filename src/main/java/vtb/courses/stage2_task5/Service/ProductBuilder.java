package vtb.courses.stage2_task5.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vtb.courses.stage2_task5.Entity.ProductType;
import vtb.courses.stage2_task5.Entity.TppProductEntity;
import vtb.courses.stage2_task5.Repository.BranchRepo;
import vtb.courses.stage2_task5.Repository.ClientRepo;
import vtb.courses.stage2_task5.Repository.ProductClassRepo;
import vtb.courses.stage2_task5.Request.CreateCsiRequest;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class ProductBuilder {
    private static ProductClassRepo productClassRepo;
    private static ClientRepo clientRepo;
    private static BranchRepo branchRepo;
    public static TppProductEntity createProductEntity(CreateCsiRequest csiRequest) {
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

    @Autowired
    public void setRegistryTypeRepo(ProductClassRepo productClassRepo) {
        ProductBuilder.productClassRepo = productClassRepo;
    }
    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        ProductBuilder.clientRepo = clientRepo;
    }
    @Autowired
    public void setBranchRepo(BranchRepo branchRepo) {
        ProductBuilder.branchRepo = branchRepo;
    }

}
