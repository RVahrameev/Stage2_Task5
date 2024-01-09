package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "tpp_ref_product_class", schema = "public", catalog = "postgres")
public class TppRefProductClassEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter
    @Column(name = "id")
    private int id;

    @Basic @Getter @Setter
    @Column(name = "value")
    private String value;

    @Basic @Getter @Setter
    @Column(name = "gbl_code")
    private String gblCode;

    @Basic @Getter @Setter
    @Column(name = "gbl_name")
    private String gblName;

    @Basic @Getter @Setter
    @Column(name = "product_row_code")
    private String productRowCode;

    @Basic @Getter @Setter
    @Column(name = "product_row_name")
    private String productRowName;

    @Basic @Getter @Setter
    @Column(name = "subclass_code")
    private String subclassCode;

    @Basic @Getter @Setter
    @Column(name = "subclass_name")
    private String subclassName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppRefProductClassEntity that = (TppRefProductClassEntity) o;
        return id == that.id && Objects.equals(value, that.value) && Objects.equals(gblCode, that.gblCode) && Objects.equals(gblName, that.gblName) && Objects.equals(productRowCode, that.productRowCode) && Objects.equals(productRowName, that.productRowName) && Objects.equals(subclassCode, that.subclassCode) && Objects.equals(subclassName, that.subclassName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, gblCode, gblName, productRowCode, productRowName, subclassCode, subclassName);
    }
}
