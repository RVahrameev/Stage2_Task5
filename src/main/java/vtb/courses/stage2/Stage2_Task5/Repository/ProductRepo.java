package vtb.courses.stage2.Stage2_Task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2.Stage2_Task5.Entity.TppClientEntity;
import vtb.courses.stage2.Stage2_Task5.Entity.TppProductEntity;

public interface ProductRepo extends JpaRepository<TppProductEntity, Integer> {
    boolean existsByNumber(String number);
}

