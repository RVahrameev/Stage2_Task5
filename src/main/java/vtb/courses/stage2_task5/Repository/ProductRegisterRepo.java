package vtb.courses.stage2_task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vtb.courses.stage2_task5.Entity.TppProductEntity;
import vtb.courses.stage2_task5.Entity.TppProductRegisterEntity;
import vtb.courses.stage2_task5.Entity.TppRefProductRegisterTypeEntity;

@Repository
public interface ProductRegisterRepo extends JpaRepository<TppProductRegisterEntity, Integer> {
    boolean existsByProductIdAndRegisterType(TppProductEntity productId, TppRefProductRegisterTypeEntity registerType);
}
