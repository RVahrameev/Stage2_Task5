package vtb.courses.stage2.Stage2_Task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2.Stage2_Task5.Entity.AccountPoolEntity;

public interface AccountPoolRepo  extends JpaRepository<AccountPoolEntity, Integer> {
    AccountPoolEntity getByBranchCodeAndCurrencyCodeAndMdmCodeAndRegisterTypeCode(
            String branchCode,
            String currencyCode,
            String mdmCode,
            String registerTypeCode
    );
}
