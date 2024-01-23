package vtb.courses.stage2_task5.Service;

import jakarta.persistence.NoResultException;
import vtb.courses.stage2_task5.Request.CreateAccountRequest;
import vtb.courses.stage2_task5.Response.CreateAccountResponse;

public interface CreateAccountServiceIntf {
    CreateAccountResponse createAccount(CreateAccountRequest accountRequest) throws NoResultException;
}
