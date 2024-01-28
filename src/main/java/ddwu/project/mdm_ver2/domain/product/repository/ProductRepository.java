package ddwu.project.mdm_ver2.domain.product.repository;

import ddwu.project.mdm_ver2.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByPriceAsc(); // 낮은 가격순
    List<Product> findAllByOrderByPriceDesc(); // 높은 가격순
    List<Product> findAllByOrderByIdDesc(); // 최신순

    List<Product> findAllByCategoryCateCodeOrderByPriceAsc(String cateCode);
    List<Product> findAllByCategoryCateCodeOrderByPriceDesc(String cateCode);
    List<Product> findAllByCategoryCateCodeOrderByIdDesc(String cateCode);
    List<Product> findAllByCategoryCateCode(String cateCode);


    long countByCategoryCateCode(String cateCode); //상품 개수 - 카테고리 분류

    List<Product> findByNameContainingIgnoreCase(String keyword); // 상품 이름으로 검색

}
