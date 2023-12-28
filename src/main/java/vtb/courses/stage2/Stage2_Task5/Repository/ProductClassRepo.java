package vtb.courses.stage2.Stage2_Task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2.Stage2_Task5.Entity.TppRefProductClassEntity;

public interface ProductClassRepo  extends JpaRepository<TppRefProductClassEntity, Integer> {
    TppRefProductClassEntity getByValue(String productCode);
}
