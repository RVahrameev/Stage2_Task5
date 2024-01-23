package vtb.courses.stage2_task5.Service;

import vtb.courses.stage2_task5.Entity.TppRefProductRegisterTypeEntity;

public interface AccountNumServiceIntf {
    String getAccountNum(String branchCode, String currencyCode, String mdmCode, TppRefProductRegisterTypeEntity registerType);
}
