package vtb.courses.stage2.Stage2_Task5.Service;

import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vtb.courses.stage2.Stage2_Task5.Entity.AccountPoolEntity;
import vtb.courses.stage2.Stage2_Task5.Entity.TppProductRegisterEntity;
import vtb.courses.stage2.Stage2_Task5.Repository.AccountPoolRepo;
import vtb.courses.stage2.Stage2_Task5.Request.CreateAccountRequest;
import vtb.courses.stage2.Stage2_Task5.Repository.ProductRegisterRepo;
import vtb.courses.stage2.Stage2_Task5.Response.CreateAccountResponse;

@Component
public class CreateAccountService {

    private static ProductRegisterRepo registerRepo;

    public CreateAccountResponse createAccount(CreateAccountRequest accountRequest){
        CreateAccountResponse accountResponse = new CreateAccountResponse();

        TppProductRegisterEntity accountEntity = new TppProductRegisterEntity(accountRequest);
        registerRepo.save(accountEntity);

        accountResponse.getData().setAccountId (accountEntity.getId().toString());
        return accountResponse;
    };

    @Autowired
    public void setRegistryTypeRepo(ProductRegisterRepo registerRepo) {
        CreateAccountService.registerRepo = registerRepo;
    }

}
