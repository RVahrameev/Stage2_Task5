package vtb.courses.stage2.Stage2_Task5.Request;

import lombok.Getter;
import lombok.Setter;

public class CreateAccountRequest {
    //Идентификатор ЭП, к которому привязывается продуктовый регистр
    @Getter @Setter
    private Integer instanceId;

    // Тип создаваемого продуктового регистра
    @Getter @Setter
    private String registryTypeCode;

    // Клиентский или внутрибанковский
    @Getter @Setter
    private String accountType;

    // 3-х значный код валюты
    @Getter @Setter
    private String currencyCode;

    // Код филиала
    @Getter @Setter
    private String branchCode;

    // Всегда «00» для ПП РО ЮЛ
    @Getter @Setter
    private String priorityCode;

    // МДМ код клиента (КЮЛ)
    @Getter @Setter
    private String mdmCode;

    // Код клиента. Только для ВИП (РЖД, ФПК). Обсуждается с клиентом (есть выбор).
    @Getter @Setter
    private String clientCode;

    // Регион принадлежности железной дороги. Только для ВИП (РЖД, ФПК)
    @Getter @Setter
    private String trainRegion;

    // Счетчик. Только для ВИП (РЖД, ФПК)
    @Getter @Setter
    private String counter;

    // Код точки продаж
    @Getter @Setter
    private String salesCode;
}
