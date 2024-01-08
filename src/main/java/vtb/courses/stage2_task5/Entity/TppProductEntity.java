package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vtb.courses.stage2_task5.Request.CreateCsiRequest;
import vtb.courses.stage2_task5.Repository.*;

import java.math.BigDecimal;
import java.util.*;

@Component
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
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_code_id", referencedColumnName = "id")
    private TppRefProductClassEntity productCodeId;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "client_id", referencedColumnName = "id")
    private TppClientEntity clientId;
    @Basic @Column(name = "type")
    ProductType type;
    @Basic @Column(name = "number")
    private String number;
    @Basic @Column(name = "priority")
    private Integer priority;
    @Basic @Column(name = "date_of_conclusion")
    private Date dateOfConclusion;
    @Basic @Column(name = "start_date_time")
    private Date startDateTime;
    @Basic @Column(name = "end_date_time")
    private Date endDateTime;
    @Basic @Column(name = "days")
    private Short days;
    @Basic @Column(name = "penalty_rate")
    private BigDecimal penaltyRate;
    @Basic @Column(name = "nso")
    private BigDecimal nso;
    @Basic @Column(name = "threshold_amount")
    private BigDecimal thresholdAmount;
    @Basic @Column(name = "interest_rate_type")
    private String interestRateType;
    @Basic @Column(name = "tax_rate")
    private BigDecimal taxRate;
    @Basic @Column(name = "reason_close")
    private String reasonClose;
    @Basic @Column(name = "state")
    private String state;
    @Basic @Column(name = "currency")
    private String currency;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "branch", referencedColumnName = "id")
    private TppBranchEntity branch;
    @OneToMany @JoinColumn(name = "agreement_id", referencedColumnName = "agreement_id")
    private List<AgreementsEntity> agreements;

    private static ProductRepo productRepo;
    private static AgreementsRepo agreementsRepo;
    private static ProductRegisterTypeRepo registerTypeRepo;
    private static ProductClassRepo productClassRepo;
    private static ClientRepo clientRepo;
    private static BranchRepo branchRepo;
    private static ProductRegisterRepo registerRepo;

    public TppProductEntity() {
    }

    public TppProductEntity(CreateCsiRequest csiRequest, List<TppRefProductRegisterTypeEntity> registerTypes) {

//        Integer productId = csiRequest.getInstanceId();
//
//        if (productId == null) {
//
//            // Проверяем корректность переданного значения в поле ProductCode
//            List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndAccountType(csiRequest.getProductCode(), "Клиентский");
//            if (registerTypes.isEmpty()) {
//                throw new NoResultException("КодПродукта =\""+csiRequest.getProductCode()+"\" не найден в Каталоге продуктов (tpp_ref_product_register_type)");
//            }
//
//            // Проверяем что нет договора с таким же номером
//            if (productRepo.existsByNumber(csiRequest.getContractNumber())) {
//                throw new IllegalArgumentException("Параметр ContractNumber \"№ договора\" "+csiRequest.getContractNumber()+" уже существует для \n ЭП с ИД "+productId);
//            }

            // Ищем класс продукта в справочнике. Он 100% должен найтись, т.к. ранее мы искали по этому коду экземпляр TppRefProductRegisterTypeEntity
            productCodeId = productClassRepo.getByValue(csiRequest.getProductCode());

            // Заполняем остальные поля
            clientId = clientRepo.getByMdmCode(csiRequest.getMdmCode());
            type = ProductType.valueOf(csiRequest.getProductType());
            number = csiRequest.getContractNumber();
            priority = csiRequest.getPriority();
            dateOfConclusion = csiRequest.getContractDate();
            startDateTime = new Date();
            endDateTime = null;
            days = 0;
            penaltyRate = BigDecimal.valueOf(csiRequest.getInterestRatePenalty());
            nso = BigDecimal.valueOf(csiRequest.getMinimalBalance());
            thresholdAmount = BigDecimal.valueOf(csiRequest.getThresholdAmount());
            interestRateType = csiRequest.getRateType();
            taxRate = BigDecimal.valueOf(csiRequest.getTaxPercentageRate());
            reasonClose = null;
            state = "OPEN";
            currency = csiRequest.getIsoCurrencyCode();
            branch = branchRepo.getByCode(csiRequest.getBranchCode());

//            // Создаём ПР
//            for (TppRefProductRegisterTypeEntity registerType: registerTypes) {
//                TppProductRegisterEntity prEntity = new TppProductRegisterEntity(this, registerType, currency, csiRequest.getBranchCode(), csiRequest.getMdmCode());
//                registerRepo.save(prEntity);
//            }

//        }
//        else {
//            Optional<TppProductEntity> product = productRepo.findById(productId);
//            // Проверяем что нашли продукт
//            if (product.isEmpty()) {
//                throw new IllegalArgumentException("Не найден договор соответствующий параметру instanceId \"Идентификатор экземпляра продукта\" = "+csiRequest.getInstanceId());
//            }
//
//            // Проверяем что нет совпадений по номерам доп.соглашений
//            for (CreateCsiRequest.Agreement agreement: csiRequest.getInstanceAgreement()) {
//                for (AgreementsEntity agreementEntity: agreements) {
//                    if (agreementEntity.getNumber() == agreement.getNumber()) {
//                        throw new IllegalArgumentException(" Параметр Number \"№ Дополнительного соглашения (сделки)\" = \""+agreement.getNumber()+"\" уже существует для ЭП с ИД "+productId);
//                    }
//                }
////                if (agreementsRepo.existsByNumberAndAgreementId(agreement.getNumber(), product.get().getAgreementId())) {
////                    throw new IllegalArgumentException(" Параметр Number \"№ Дополнительного соглашения (сделки)\" = \""+agreement.getNumber()+"\" уже существует для ЭП с ИД "+productId);
////                }
//                // Добавляем новое доп.соглашение
//                agreements.add(new AgreementsEntity(agreement.getNumber()));
//            }
//
//        }

    }

    public void addAgreement(AgreementsEntity agreement){
        agreements.add(agreement);
    }

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

    public TppRefProductClassEntity getProductCodeId() {
        return productCodeId;
    }

    public void setProductCodeId(TppRefProductClassEntity productCodeId) {
        this.productCodeId = productCodeId;
    }

    public TppClientEntity getClientId() {
        return clientId;
    }

    public void setClientId(TppClientEntity clientId) {
        this.clientId = clientId;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
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

    public TppBranchEntity getBranch() {
        return branch;
    }

    public void setBranch(TppBranchEntity branch) {
        this.branch = branch;
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

    @Autowired
    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
    @Autowired
    public void setAgreementsRepo(AgreementsRepo agreementsRepo) {
        this.agreementsRepo = agreementsRepo;
    }
    @Autowired
    public void setRegistryTypeRepo(ProductRegisterTypeRepo registryTypeRepo) {
        this.registerTypeRepo = registryTypeRepo;
    }
    @Autowired
    public void setRegistryTypeRepo(ProductClassRepo productClassRepo) {
        this.productClassRepo = productClassRepo;
    }
    @Autowired
    public void setClientRepo(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }
    @Autowired
    public void setBranchRepo(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }
    @Autowired
    public static void setRegisterRepo(ProductRegisterRepo registerRepo) {
        System.out.println("setRegisterRepo");
        TppProductEntity.registerRepo = registerRepo;
    }
}
