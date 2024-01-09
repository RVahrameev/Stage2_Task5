package vtb.courses.stage2_task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vtb.courses.stage2_task5.Entity.TppRefProductRegisterTypeEntity;

import java.util.List;

@Repository
public interface ProductRegisterTypeRepo extends JpaRepository<TppRefProductRegisterTypeEntity, Integer> {

    List<TppRefProductRegisterTypeEntity> findAllByProductClassCodeAndAccountType(String productClassCode, String accountType);

    List<TppRefProductRegisterTypeEntity> findAllByProductClassCodeAndValue(String productClassCode, String registerTypeCode);

    TppRefProductRegisterTypeEntity getByValue(String typeCode);
}
