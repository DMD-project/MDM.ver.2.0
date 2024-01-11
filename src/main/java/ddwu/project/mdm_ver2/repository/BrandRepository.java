package ddwu.project.mdm_ver2.repository;

import ddwu.project.mdm_ver2.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository  extends JpaRepository<Brand, Long> {
    Brand findByBrandCode(String brandCode);

}
