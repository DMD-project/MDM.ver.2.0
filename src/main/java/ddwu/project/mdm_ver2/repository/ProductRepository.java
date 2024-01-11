package ddwu.project.mdm_ver2.repository;

import ddwu.project.mdm_ver2.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
