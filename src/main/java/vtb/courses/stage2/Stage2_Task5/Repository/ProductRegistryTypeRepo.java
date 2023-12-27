package vtb.courses.stage2.Stage2_Task5.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2.Stage2_Task5.Entity.TppRefProductRegisterTypeEntity;

import java.util.List;

public interface ProductRegistryTypeRepo extends JpaRepository<TppRefProductRegisterTypeEntity, Integer> {
    boolean existsByProductClassCodeAndAccountType(String productClassCode, String accountType);

    List<TppRefProductRegisterTypeEntity> findAllByProductClassCodeAndAccountType(String productClassCode, String accountType);
}
