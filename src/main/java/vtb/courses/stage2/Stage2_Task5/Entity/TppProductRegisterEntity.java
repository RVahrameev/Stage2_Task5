package vtb.courses.stage2.Stage2_Task5.Entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vtb.courses.stage2.Stage2_Task5.Repository.*;
import vtb.courses.stage2.Stage2_Task5.Request.CreateAccountRequest;
import vtb.courses.stage2.Stage2_Task5.Response.CreateAccountResponse;
import vtb.courses.stage2.Stage2_Task5.Service.CreateAccountService;

import java.util.List;
import java.util.Objects;

@Component
@Entity
@Table(name = "tpp_product_register", schema = "public", catalog = "postgres")
public class TppProductRegisterEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id", referencedColumnName = "id")
    private TppProductEntity productId;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "register_type", referencedColumnName = "id")
    private TppRefProductRegisterTypeEntity registerType;
    @Basic
    @Column(name = "account_num")
    private String accountNum;
    @Basic
    @Column(name = "currency")
    private String currency;
    @Basic
    @Column(name = "state")
    private String state;

    private static ProductRegisterRepo registerRepo;
    private static ProductRegisterTypeRepo registerTypeRepo;
    private static ProductRepo productRepo;
    private static AccountPoolRepo accountPoolRepo;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TppProductEntity getProductId() {
        return productId;
    }

    public void setProductId(TppProductEntity productId) {
        this.productId = productId;
    }

    public TppRefProductRegisterTypeEntity getRegisterType() {
        return registerType;
    }

    public void setRegisterType(TppRefProductRegisterTypeEntity registerType) {
        this.registerType = registerType;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppProductRegisterEntity that = (TppProductRegisterEntity) o;
        return id == that.id && Objects.equals(productId, that.productId) && Objects.equals(registerType, that.registerType) && Objects.equals(accountNum, that.accountNum) && Objects.equals(currency, that.currency) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, registerType, accountNum, currency, state);
    }

    public TppProductRegisterEntity() {
    }

    public TppProductRegisterEntity(CreateAccountRequest accountRequest) {
        productId = productRepo.getReferenceById(accountRequest.getInstanceId());
        if (productId == null) {
            throw new NoResultException("По instanceId \"Идентификатор ЭП\" <"+accountRequest.getInstanceId()+"> не найден экземпляр продукта.");
        }
        // Проверяем на дубли
        if (registerRepo.existsByProductIdAndRegisterType(accountRequest.getInstanceId(), registerTypeRepo.getByValue(accountRequest.getRegistryTypeCode()))) {
            throw new IllegalArgumentException("Параметр registryTypeCode \"Тип регистра\" <"+accountRequest.getRegistryTypeCode()+"> уже существует для ЭП с ИД <"+accountRequest.getInstanceId()+">.");
        }

        List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndValue(productId.getProductCodeId().getValue(), accountRequest.getRegistryTypeCode());
        if (registerTypes.isEmpty()) {
            throw new NoResultException("КодПродукта <"+productId.getProductCodeId().getValue()+"> не найдено в Каталоге продуктов для данного типа Регистра \""+accountRequest.getRegistryTypeCode()+"\"");
        }

        AccountPoolEntity accountPool = accountPoolRepo.getByBranchCodeAndCurrencyCodeAndMdmCodeAndRegisterTypeCode(
                accountRequest.getBranchCode(),
                accountRequest.getCurrencyCode(),
                accountRequest.getMdmCode(),
                accountRequest.getRegistryTypeCode()
        );

        if (accountPool.getAccounts().isEmpty()) {
            throw new NoResultException("В пуле счетов закончились счета");
        }

        // Заполняем поля
        accountNum = accountPool.getAccounts().get(0);
        currency = accountRequest.getCurrencyCode();
        state = "OPEN";

        // Удаляем счёт из пула
        accountPool.getAccounts().remove(0);
    }

    @Autowired
    public static void setRegisterRepo(ProductRegisterRepo registerRepo) {
        TppProductRegisterEntity.registerRepo = registerRepo;
    }
    @Autowired
    public static void setRegisterTypeRepo(ProductRegisterTypeRepo registerTypeRepo) {
        TppProductRegisterEntity.registerTypeRepo = registerTypeRepo;
    }
    @Autowired
    public static void setProductRepo(ProductRepo productRepo) {
        TppProductRegisterEntity.productRepo = productRepo;
    }
    @Autowired
    public static void setAccountPoolRepo(AccountPoolRepo accountPoolRepo) {
        TppProductRegisterEntity.accountPoolRepo = accountPoolRepo;
    }

}
