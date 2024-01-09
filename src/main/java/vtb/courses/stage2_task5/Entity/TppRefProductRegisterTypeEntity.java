package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "tpp_ref_product_register_type", schema = "public", catalog = "postgres")
public class TppRefProductRegisterTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter
    @Column(name = "id")
    private int id;

    @Basic @Getter @Setter
    @Column(name = "value")
    private String value;

    @Basic @Getter @Setter
    @Column(name = "register_type_name")
    private String registerTypeName;

    @Basic @Getter @Setter
    @Column(name = "product_class_code")
    private String productClassCode;

    @Basic @Getter @Setter
    @Column(name = "account_type")
    private String accountType;

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
