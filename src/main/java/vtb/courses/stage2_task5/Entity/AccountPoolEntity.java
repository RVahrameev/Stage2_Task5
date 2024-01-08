package vtb.courses.stage2_task5.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "account_pool", schema = "public", catalog = "postgres")
public class AccountPoolEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "branch_code")
    private String branchCode;
    @Basic
    @Column(name = "currency_code")
    private String currencyCode;
    @Basic
    @Column(name = "mdm_code")
    private String mdmCode;
    @Basic
    @Column(name = "priority_code")
    private String priorityCode;
    @Basic
    @Column(name = "register_type_code")
    private String registerTypeCode;
    @ElementCollection
    @CollectionTable(name = "account", joinColumns = @JoinColumn(name = "account_coll"))
    @Column(name = "account")
    private List<String> accounts = new ArrayList<>();

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getMdmCode() {
        return mdmCode;
    }

    public void setMdmCode(String mdmCode) {
        this.mdmCode = mdmCode;
    }

    public String getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public String getRegisterTypeCode() {
        return registerTypeCode;
    }

    public void setRegisterTypeCode(String registerTypeCode) {
        this.registerTypeCode = registerTypeCode;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountPoolEntity that = (AccountPoolEntity) o;
        return Objects.equals(branchCode, that.branchCode) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(mdmCode, that.mdmCode) && Objects.equals(priorityCode, that.priorityCode) && Objects.equals(registerTypeCode, that.registerTypeCode) && Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchCode, currencyCode, mdmCode, priorityCode, registerTypeCode, accounts);
    }
}
