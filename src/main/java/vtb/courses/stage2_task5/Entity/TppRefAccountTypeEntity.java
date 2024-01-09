package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "tpp_ref_account_type", schema = "public", catalog = "postgres")
public class TppRefAccountTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter
    @Column(name = "id")
    private int id;

    @Basic @Getter @Setter
    @Column(name = "value")
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppRefAccountTypeEntity that = (TppRefAccountTypeEntity) o;
        return id == that.id && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }
}
