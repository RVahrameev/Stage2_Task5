package vtb.courses.stage2.Stage2_Task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2.Stage2_Task5.Entity.TppProductRegisterEntity;

public interface ProductRegisterRepo extends JpaRepository<TppProductRegisterEntity, Integer> {
    boolean existsByProductIdAndRegisterType(Integer productId, Integer registerType);
}
