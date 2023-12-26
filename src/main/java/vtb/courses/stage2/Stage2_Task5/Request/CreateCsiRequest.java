package vtb.courses.stage2.Stage2_Task5.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class CreateCsiRequest {
    @Getter @Setter
    private int instanceId;
    @Getter @Setter
    private String productType;
    @Getter @Setter
    private String productCode;
    @Getter @Setter
    private String registerType;
    @Getter @Setter
    private String mdmCode;
    @Getter @Setter
    private String contractNumber;
    @Getter @Setter
    private Date contractDate;
    @Getter @Setter
    private int priority;
    @Getter @Setter
    private double interestRatePenalty;
    @Getter @Setter
    private double minimalBalance;
    @Getter @Setter
    private double thresholdAmount;
    @Getter @Setter
    private String accountingDetails;
    @Getter @Setter
    private String rateType;
    @Getter @Setter
    private double taxPercentageRate;
    @Getter @Setter
    private double technicalOverdraftLimitAmount;
    @Getter @Setter
    private int contractId;
    @Getter @Setter
    private String BranchCode;
    @Getter @Setter
    private Agreement[] instanceAgreement;

    class Agreement {
        @Getter @Setter
        private int supplementaryAgreementId;
        @Getter @Setter
        private String number;
    }
}
