package vtb.courses.stage2_task5.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
public class CreateCsiRequest {
    private static com.networknt.schema.JsonSchema jsonSchema = JsonSchemaUtil.getJsonSchema(CreateCsiRequest.class);

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

    @NoArgsConstructor
    @ToString
    public static class Agreement {
        @Getter @Setter
        private Integer supplementaryAgreementId;
        @Getter @Setter
        private String number;
    }

    @NoArgsConstructor
    @ToString
    public static class AdditionalPropertiesVip {
        @Getter @Setter
        private Data[] data;
    }

    @NoArgsConstructor
    public static class Data {
        @Getter @Setter
        private String key;
        @Getter @Setter
        private String value;
        @Getter @Setter
        private String name;
    }

    public static com.networknt.schema.JsonSchema getJsonSchema() { return  jsonSchema; };

}

