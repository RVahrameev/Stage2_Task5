package vtb.courses.stage2_task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2_task5.Entity.AgreementsEntity;

public interface AgreementsRepo extends JpaRepository<AgreementsEntity, Integer> {
}
