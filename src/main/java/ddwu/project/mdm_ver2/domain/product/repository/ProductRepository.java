package ddwu.project.mdm_ver2.domain.product.repository;

import ddwu.project.mdm_ver2.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long prodId);

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findAllByOrderByIdDesc();

    List<Product> findAllByCategoryCateCodeOrderByPriceAsc(String cateCode);

    List<Product> findAllByCategoryCateCodeOrderByPriceDesc(String cateCode);

    List<Product> findAllByCategoryCateCodeOrderByIdDesc(String cateCode);

    long countByCategoryCateCode(String cateCode);

    List<Product> findByNameContainingIgnoreCase(String keyword);
}