package ddwu.project.mdm_ver2.repository;

import ddwu.project.mdm_ver2.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByPriceAsc(); // 낮은 가격순
    List<Product> findAllByOrderByPriceDesc(); // 높은 가격순
    List<Product> findAllByOrderByIdDesc(); // 최신순
}
