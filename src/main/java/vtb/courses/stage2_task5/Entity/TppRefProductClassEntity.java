package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tpp_ref_product_class", schema = "public", catalog = "postgres")
public class TppRefProductClassEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "value")
    private String value;
    @Basic
    @Column(name = "gbl_code")
    private String gblCode;
    @Basic
    @Column(name = "gbl_name")
    private String gblName;
    @Basic
    @Column(name = "product_row_code")
    private String productRowCode;
    @Basic
    @Column(name = "product_row_name")
    private String productRowName;
    @Basic
    @Column(name = "subclass_code")
    private String subclassCode;
    @Basic
    @Column(name = "subclass_name")
    private String subclassName;

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

    public String getGblCode() {
        return gblCode;
    }

    public void setGblCode(String gblCode) {
        this.gblCode = gblCode;
    }

    public String getGblName() {
        return gblName;
    }

    public void setGblName(String gblName) {
        this.gblName = gblName;
    }

    public String getProductRowCode() {
        return productRowCode;
    }

    public void setProductRowCode(String productRowCode) {
        this.productRowCode = productRowCode;
    }

    public String getProductRowName() {
        return productRowName;
    }

    public void setProductRowName(String productRowName) {
        this.productRowName = productRowName;
    }

    public String getSubclassCode() {
        return subclassCode;
    }

    public void setSubclassCode(String subclassCode) {
        this.subclassCode = subclassCode;
    }

    public String getSubclassName() {
        return subclassName;
    }

    public void setSubclassName(String subclassName) {
        this.subclassName = subclassName;
    }

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
