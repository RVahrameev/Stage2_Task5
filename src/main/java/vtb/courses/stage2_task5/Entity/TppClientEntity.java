package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "tpp_client", schema = "public", catalog = "postgres")
public class TppClientEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter
    @Column(name = "id")
    private int id;

    @Basic @Getter @Setter
    @Column(name = "mdm_code")
    private String mdmCode;

    @Basic @Getter @Setter
    @Column(name = "name")
    private String name;

    @Basic @Getter @Setter
    @Column(name = "kpp")
    private String kpp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppClientEntity that = (TppClientEntity) o;
        return id == that.id && Objects.equals(mdmCode, that.mdmCode) && Objects.equals(name, that.name) && Objects.equals(kpp, that.kpp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mdmCode, name, kpp);
    }
}
