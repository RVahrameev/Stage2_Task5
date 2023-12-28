package vtb.courses.stage2.Stage2_Task5.Entity;

import jakarta.persistence.*;

import java.io.StringBufferInputStream;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tpp_client", schema = "public", catalog = "postgres")
public class TppClientEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "mdm_code")
    private String mdmCode;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "kpp")
    private String kpp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMdmCode() {
        return mdmCode;
    }

    public void setMdmCode(String mdmCode) {
        this.mdmCode = mdmCode;
    }

    public String getFio() {
        return name;
    }

    public void setFio(String fio) {
        this.name = fio;
    }

    public String getBirthday() {
        return kpp;
    }

    public void setBirthday(String kpp) {
        this.kpp = kpp;
    }

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
