package vtb.courses.stage2_task5.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vtb.courses.stage2_task5.Entity.TppRefAccountTypeEntity;

@Repository
public interface AccountTypeRepo extends JpaRepository<TppRefAccountTypeEntity, Integer> {
    TppRefAccountTypeEntity getByValue(String accountType);
}
