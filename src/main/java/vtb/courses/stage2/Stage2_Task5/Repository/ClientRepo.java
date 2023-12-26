package vtb.courses.stage2.Stage2_Task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2.Stage2_Task5.Entity.TppClientEntity;

public interface ClientRepo extends JpaRepository<TppClientEntity, Integer> {
}

