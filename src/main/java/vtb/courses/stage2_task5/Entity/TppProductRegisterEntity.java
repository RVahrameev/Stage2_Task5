package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "tpp_product_register", schema = "public", catalog = "postgres")
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppProductRegisterEntity that = (TppProductRegisterEntity) o;
        return id.equals(that.id) && Objects.equals(productId, that.productId) && Objects.equals(registerType, that.registerType) && Objects.equals(accountNum, that.accountNum) && Objects.equals(currency, that.currency) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, registerType, accountNum, currency, state);
    }

    public TppProductRegisterEntity(TppProductEntity productId, TppRefProductRegisterTypeEntity registerType, String accountNum, String currency) {
        this.productId = productId;
        this.registerType = registerType;
        this.accountNum = accountNum;
        this.currency = currency;
        this.state = AccountState.OPEN;
    }

}
