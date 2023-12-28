package vtb.courses.stage2.Stage2_Task5.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tpp_ref_product_register_type", schema = "public", catalog = "postgres")
public class TppRefProductRegisterTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "value")
    private String value;
    @Basic
    @Column(name = "register_type_name")
    private String registerTypeName;
    @Basic
    @Column(name = "product_class_code")
    private String productClassCode;
    @Basic
    @Column(name = "account_type")
    private String accountType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRegisterTypeName() {
        return registerTypeName;
    }

    public void setRegisterTypeName(String registerTypeName) {
        this.registerTypeName = registerTypeName;
    }

    public String getProductClassCode() {
        return productClassCode;
    }

    public void setProductClassCode(String productClassCode) {
        this.productClassCode = productClassCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppRefProductRegisterTypeEntity that = (TppRefProductRegisterTypeEntity) o;
        return id == that.id && Objects.equals(value, that.value) && Objects.equals(registerTypeName, that.registerTypeName) && Objects.equals(productClassCode, that.productClassCode)  && Objects.equals(accountType, that.accountType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, registerTypeName, productClassCode, accountType);
    }
}
