package vtb.courses.stage2_task5.Request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateAccountRequest {
    private static final com.networknt.schema.JsonSchema jsonSchema = JsonSchemaUtil.getJsonSchema(CreateAccountRequest.class);

    //Идентификатор ЭП, к которому привязывается продуктовый регистр
    private Integer instanceId;

    // Тип создаваемого продуктового регистра
    private String registryTypeCode;

    // Клиентский или внутрибанковский
    private String accountType;

    // 3-х значный код валюты
    private String currencyCode;

    // Код филиала
    private String branchCode;

    // Всегда «00» для ПП РО ЮЛ
    private String priorityCode;

    // МДМ код клиента (КЮЛ)
    private String mdmCode;

    // Код клиента. Только для ВИП (РЖД, ФПК). Обсуждается с клиентом (есть выбор).
    private String clientCode;

    // Регион принадлежности железной дороги. Только для ВИП (РЖД, ФПК)
    private String trainRegion;

    // Счетчик. Только для ВИП (РЖД, ФПК)
    private String counter;

    // Код точки продаж
    private String salesCode;
    public static com.networknt.schema.JsonSchema getJsonSchema() { return  jsonSchema; }
}
