package vtb.courses.stage2.Stage2_Task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2.Stage2_Task5.Entity.AgreementsEntity;

public interface AgreementsRepo extends JpaRepository<AgreementsEntity, Integer> {
    boolean existsByNumberAndAgreementId(String number, Integer agreementId);
}


