package vtb.courses.stage2.Stage2_Task5.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tpp_product", schema = "public", catalog = "postgres")
public class TppProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_id")
    private int agreementId;
    @Basic
    @Column(name = "product_code_id")
    private Integer productCodeId;
    @Basic
    @Column(name = "client_id")
    private Integer clientId;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "number")
    private String number;
    @Basic
    @Column(name = "priority")
    private Short priority;
    @Basic
    @Column(name = "date_of_conclusion")
    private Date dateOfConclusion;
    @Basic
    @Column(name = "start_date_time")
    private Date startDateTime;
    @Basic
    @Column(name = "end_date_time")
    private Date endDateTime;
    @Basic
    @Column(name = "days")
    private Short days;
    @Basic
    @Column(name = "penalty_rate")
    private BigDecimal penaltyRate;
    @Basic
    @Column(name = "nso")
    private BigDecimal nso;
    @Basic
    @Column(name = "threshold_amount")
    private BigDecimal thresholdAmount;
    @Basic
    @Column(name = "register_type")
    private Integer registerType;
    @Basic
    @Column(name = "interest_rate_type")
    private String interestRateType;
    @Basic
    @Column(name = "tax_rate")
    private BigDecimal taxRate;
    @Basic
    @Column(name = "reason_close")
    private String reasonClose;
    @Basic
    @Column(name = "state")
    private String state;
    @Basic
    @Column(name = "currency")
    private String currency;
    @Basic
    @Column(name = "branch")
    private Integer branch;

    @OneToMany @JoinColumn(name = "agreement_id", referencedColumnName = "agreement_id")
    private List<AgreementsEntity> agreements;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(int agreementId) {
        this.agreementId = agreementId;
    }

    public Integer getProductCodeId() {
        return productCodeId;
    }

    public void setProductCodeId(Integer productCodeId) {
        this.productCodeId = productCodeId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public Date getDateOfConclusion() {
        return dateOfConclusion;
    }

    public void setDateOfConclusion(Date dateOfConclusion) {
        this.dateOfConclusion = dateOfConclusion;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Short getDays() {
        return days;
    }

    public void setDays(Short days) {
        this.days = days;
    }

    public BigDecimal getPenaltyRate() {
        return penaltyRate;
    }

    public void setPenaltyRate(BigDecimal penaltyRate) {
        this.penaltyRate = penaltyRate;
    }

    public BigDecimal getNso() {
        return nso;
    }

    public void setNso(BigDecimal nso) {
        this.nso = nso;
    }

    public BigDecimal getThresholdAmount() {
        return thresholdAmount;
    }

    public void setThresholdAmount(BigDecimal thresholdAmount) {
        this.thresholdAmount = thresholdAmount;
    }

    public Integer getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }

    public String getInterestRateType() {
        return interestRateType;
    }

    public void setInterestRateType(String interestRateType) {
        this.interestRateType = interestRateType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getReasonClose() {
        return reasonClose;
    }

    public void setReasonClose(String reasonClose) {
        this.reasonClose = reasonClose;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppProductEntity that = (TppProductEntity) o;
        return id == that.id && Objects.equals(productCodeId, that.productCodeId) && Objects.equals(clientId, that.clientId) && Objects.equals(type, that.type) && Objects.equals(number, that.number) && Objects.equals(priority, that.priority) && Objects.equals(dateOfConclusion, that.dateOfConclusion) && Objects.equals(startDateTime, that.startDateTime) && Objects.equals(endDateTime, that.endDateTime) && Objects.equals(days, that.days) && Objects.equals(penaltyRate, that.penaltyRate) && Objects.equals(nso, that.nso) && Objects.equals(thresholdAmount, that.thresholdAmount) && Objects.equals(registerType, that.registerType) && Objects.equals(interestRateType, that.interestRateType) && Objects.equals(taxRate, that.taxRate) && Objects.equals(reasonClose, that.reasonClose) && Objects.equals(state, that.state) && Objects.equals(currency, that.currency) && Objects.equals(branch, that.branch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productCodeId, clientId, type, number, priority, dateOfConclusion, startDateTime, endDateTime, days, penaltyRate, nso, thresholdAmount, registerType, interestRateType, taxRate, reasonClose, state, currency, branch);
    }
}
