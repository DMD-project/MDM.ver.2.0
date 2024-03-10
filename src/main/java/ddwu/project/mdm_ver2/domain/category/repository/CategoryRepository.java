package ddwu.project.mdm_ver2.domain.category.repository;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCateCode(String cateCode);

    boolean existsByCateCode(String cateCode);

}
