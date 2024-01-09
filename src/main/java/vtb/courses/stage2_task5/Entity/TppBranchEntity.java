package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "tpp_branch", schema = "public", catalog = "postgres")
public class TppBranchEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter
    @Column(name = "id")
    private int id;
    @Basic @Getter @Setter
    @Column(name = "code")
    private String code;
    @Basic @Getter @Setter
    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppBranchEntity that = (TppBranchEntity) o;
        return id == that.id && Objects.equals(code, that.code) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name);
    }
}
