package vtb.courses.stage2_task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2_task5.Entity.TppBranchEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepo extends JpaRepository<TppBranchEntity, Integer> {
    TppBranchEntity getByCode(String branchCode);
}
