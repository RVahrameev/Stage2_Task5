package vtb.courses.stage2.Stage2_Task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vtb.courses.stage2.Stage2_Task5.Entity.TppRefAccountTypeEntity;

public interface AccountTypeRepo extends JpaRepository<TppRefAccountTypeEntity, Integer> {
    TppRefAccountTypeEntity getByValue(String accountType);
}
