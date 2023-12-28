package vtb.courses.stage2.Stage2_Task5.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
public class CreateCsiRequest {
    @Getter @Setter
    private Integer instanceId;
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
    private Integer priority;
    @Getter @Setter
    private Double interestRatePenalty;
    @Getter @Setter
    private Double minimalBalance;
    @Getter @Setter
    private Double thresholdAmount;
    @Getter @Setter
    private String accountingDetails;
    @Getter @Setter
    private String rateType;
    @Getter @Setter
    private Double taxPercentageRate;
    @Getter @Setter
    private Double technicalOverdraftLimitAmount;
    @Getter @Setter
    private Integer contractId;
    @Getter @Setter
    private String isoCurrencyCode;
    @Getter @Setter
    private String branchCode;
    @Getter @Setter
    private Agreement[] instanceAgreement;
    @Getter @Setter
    private AdditionalPropertiesVip additionalPropertiesVip;

    public class Agreement {
        @Getter @Setter
        private Integer supplementaryAgreementId;
        @Getter @Setter
        private String number;
    }

    public class AdditionalPropertiesVip {
        private Data[] data;
    }
    public class Data {
        private String key;
        private String value;
        private String name;
    }
}
