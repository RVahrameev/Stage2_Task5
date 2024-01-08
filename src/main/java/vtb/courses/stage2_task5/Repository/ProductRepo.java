package vtb.courses.stage2_task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import vtb.courses.stage2_task5.Entity.TppProductEntity;

@Repository
public interface ProductRepo extends JpaRepository<TppProductEntity, Integer> {
    boolean existsByNumber(String number);
    TppProductEntity getByNumber(String number);
}

