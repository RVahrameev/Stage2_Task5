package vtb.courses.stage2.Stage2_Task5.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tpp_product_register", schema = "public", catalog = "postgres")
public class TppProductRegisterEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @Column(name = "register_type")
    private Integer registerType;
    @Basic
    @Column(name = "account_num")
    private String accountNum;
    @Basic
    @Column(name = "client")
    private Integer client;
    @Basic
    @Column(name = "currency")
    private String currency;
    @Basic
    @Column(name = "branch")
    private Integer branch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppProductRegisterEntity that = (TppProductRegisterEntity) o;
        return id == that.id && Objects.equals(productId, that.productId) && Objects.equals(registerType, that.registerType) && Objects.equals(accountNum, that.accountNum) && Objects.equals(client, that.client) && Objects.equals(currency, that.currency) && Objects.equals(branch, that.branch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, registerType, accountNum, client, currency, branch);
    }
}
