package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "agreements", schema = "public", catalog = "postgres")
public class AgreementsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "agreement_id")
    private Integer agreementId;
    @Basic
    @Column(name = "number")
    private String number;

    public AgreementsEntity() {
    }

    public AgreementsEntity(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(Integer agreementId) {
        this.agreementId = agreementId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgreementsEntity that = (AgreementsEntity) o;
        return id == that.id && Objects.equals(agreementId, that.agreementId) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, agreementId, number);
    }
}
