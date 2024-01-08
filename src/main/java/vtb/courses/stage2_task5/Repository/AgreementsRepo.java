package vtb.courses.stage2_task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vtb.courses.stage2_task5.Entity.AgreementsEntity;

@Repository
public interface AgreementsRepo extends JpaRepository<AgreementsEntity, Integer> {

    boolean existsByNumberAndAgreementId(String number, Integer agreementId);
}


