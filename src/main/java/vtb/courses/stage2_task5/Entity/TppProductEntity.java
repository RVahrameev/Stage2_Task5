package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@NoArgsConstructor
@ToString
@Component
@Entity
@Table(name = "tpp_product", schema = "public", catalog = "postgres")
public class TppProductEntity {
    @Id @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_id", insertable = false, updatable = false)
    private int agreementId;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_code_id", referencedColumnName = "id")
    private TppRefProductClassEntity productCodeId;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "client_id", referencedColumnName = "id")
    private TppClientEntity clientId;

    @Basic @Getter @Setter
    @Column(name = "type")
    ProductType type;

    @Basic @Getter @Setter
    @Column(name = "number")
    private String number;

    @Basic @Getter @Setter
    @Column(name = "priority")
    private Integer priority;

    @Basic @Getter @Setter
    @Column(name = "date_of_conclusion")
    private Date dateOfConclusion;

    @Basic @Getter @Setter
    @Column(name = "start_date_time")
    private Date startDateTime;

    @Basic @Getter @Setter
    @Column(name = "end_date_time")
    private Date endDateTime;

    @Basic @Getter @Setter
    @Column(name = "days")
    private Short days;

    @Basic @Getter @Setter
    @Column(name = "penalty_rate")
    private BigDecimal penaltyRate;

    @Basic @Getter @Setter
    @Column(name = "nso")
    private BigDecimal nso;

    @Basic @Getter @Setter
    @Column(name = "threshold_amount")
    private BigDecimal thresholdAmount;

    @Basic @Getter @Setter
    @Column(name = "interest_rate_type")
    private String interestRateType;

    @Basic @Getter @Setter
    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @Basic @Getter @Setter
    @Column(name = "reason_close")
    private String reasonClose;

    @Basic @Getter @Setter
    @Column(name = "state")
    private String state;

    @Basic @Getter @Setter
    @Column(name = "currency")
    private String currency;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "branch", referencedColumnName = "id")
    private TppBranchEntity branch;

    @OneToMany(mappedBy = "agreementId")
    private List<AgreementsEntity> agreements = new ArrayList<>();

    public void addAgreement(AgreementsEntity agreement){
        agreement.setAgreementId(this);
        agreements.add(agreement);
    }

    public Iterator<AgreementsEntity> getAgreements() {
        return agreements.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppProductEntity that = (TppProductEntity) o;
        return id == that.id && Objects.equals(productCodeId, that.productCodeId) && Objects.equals(clientId, that.clientId) && Objects.equals(type, that.type) && Objects.equals(number, that.number) && Objects.equals(priority, that.priority) && Objects.equals(dateOfConclusion, that.dateOfConclusion) && Objects.equals(startDateTime, that.startDateTime) && Objects.equals(endDateTime, that.endDateTime) && Objects.equals(days, that.days) && Objects.equals(penaltyRate, that.penaltyRate) && Objects.equals(nso, that.nso) && Objects.equals(thresholdAmount, that.thresholdAmount) && Objects.equals(interestRateType, that.interestRateType) && Objects.equals(taxRate, that.taxRate) && Objects.equals(reasonClose, that.reasonClose) && Objects.equals(state, that.state) && Objects.equals(currency, that.currency) && Objects.equals(branch, that.branch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productCodeId, clientId, type, number, priority, dateOfConclusion, startDateTime, endDateTime, days, penaltyRate, nso, thresholdAmount, interestRateType, taxRate, reasonClose, state, currency, branch);
    }

}
