package vtb.courses.stage2_task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vtb.courses.stage2_task5.Entity.TppRefProductClassEntity;

@Repository
public interface ProductClassRepo  extends JpaRepository<TppRefProductClassEntity, Integer> {
    TppRefProductClassEntity getByValue(String productCode);
}
