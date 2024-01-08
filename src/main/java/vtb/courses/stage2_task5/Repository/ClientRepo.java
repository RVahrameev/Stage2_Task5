package vtb.courses.stage2_task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vtb.courses.stage2_task5.Entity.TppClientEntity;

@Repository
public interface ClientRepo extends JpaRepository<TppClientEntity, Integer> {
    TppClientEntity getByMdmCode(String mdmCode);
}

