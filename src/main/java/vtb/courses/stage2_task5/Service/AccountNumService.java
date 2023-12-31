package vtb.courses.stage2_task5.Service;

import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vtb.courses.stage2_task5.Entity.AccountPoolEntity;
import vtb.courses.stage2_task5.Entity.TppRefProductRegisterTypeEntity;
import vtb.courses.stage2_task5.Repository.AccountPoolRepo;

@Service
public class AccountNumService {

    private AccountPoolRepo accountPoolRepo;

    public String getAccountNum(String branchCode, String currencyCode, String mdmCode, TppRefProductRegisterTypeEntity registerType) {
        AccountPoolEntity accountPool = accountPoolRepo.getByBranchCodeAndCurrencyCodeAndMdmCodeAndRegisterTypeCode(
                branchCode,
                currencyCode,
                mdmCode,
                registerType.getValue()
        );

        if (accountPool.getAccounts().isEmpty()) {
            throw new NoResultException("В пуле счетов закончились счета");
        }

        String retAccountNum = accountPool.getAccounts().get(0);

        // Удаляем счёт из пула
        accountPool.getAccounts().remove(0);

        return retAccountNum;
    }
    @Autowired
    public void setAccountPoolRepo(AccountPoolRepo accountPoolRepo) {
        System.out.println(this + "   setAccountPoolRepo " + accountPoolRepo);
        this.accountPoolRepo = accountPoolRepo;
    }
}
