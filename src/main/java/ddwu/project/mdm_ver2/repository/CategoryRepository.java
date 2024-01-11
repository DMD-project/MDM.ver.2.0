package ddwu.project.mdm_ver2.repository;

import ddwu.project.mdm_ver2.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCateCode(String cateCode);
}
