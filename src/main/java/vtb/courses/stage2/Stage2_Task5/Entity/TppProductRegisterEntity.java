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
    private AccountState state;

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

    public AccountState getState() {
        return state;
    }

    public void setState(AccountState state) {
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

    public TppProductRegisterEntity(TppProductEntity productId, TppRefProductRegisterTypeEntity registerType, String currency, String branchCode, String mdmCode) {
        this.productId = productId;
        this.registerType = registerType;
        AccountPoolEntity accountPool = accountPoolRepo.getByBranchCodeAndCurrencyCodeAndMdmCodeAndRegisterTypeCode(
                branchCode,
                currency,
                mdmCode,
                registerType.getValue()
        );

        if (accountPool.getAccounts().isEmpty()) {
            throw new NoResultException("В пуле счетов закончились счета");
        }

        // Заполняем поля
        this.accountNum = accountPool.getAccounts().get(0);
        this.currency = currency;
        this.state = AccountState.OPEN;

        // Удаляем счёт из пула
        accountPool.getAccounts().remove(0);
    }

    @Autowired
    public static void setAccountPoolRepo(AccountPoolRepo accountPoolRepo) {
        TppProductRegisterEntity.accountPoolRepo = accountPoolRepo;
    }

}
