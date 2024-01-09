package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Entity
@Table(name = "agreements", schema = "public", catalog = "postgres")
public class AgreementsEntity {
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "agreement_id", referencedColumnName = "agreement_id")
    private TppProductEntity agreementId;

    @Basic @Getter @Setter
    @Column(name = "number")
    private String number;

    public AgreementsEntity(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgreementsEntity that = (AgreementsEntity) o;
        return id == that.id;// && Objects.equals(agreementId, that.agreementId) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return 1;//Objects.hash(id, agreementId, number);
    }
}
