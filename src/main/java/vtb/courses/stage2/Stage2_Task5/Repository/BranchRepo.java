package vtb.courses.stage2.Stage2_Task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2.Stage2_Task5.Entity.TppBranchEntity;

public interface BranchRepo extends JpaRepository<TppBranchEntity, Integer> {
    TppBranchEntity getByCode(String branchCode);
}
