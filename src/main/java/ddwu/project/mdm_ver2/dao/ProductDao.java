package ddwu.project.mdm_ver2.dao;

import ddwu.project.mdm_ver2.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    List<Product> findByNameContaining(String name);
    List<Product> findByCateID(int cateID);
    List<Product> findAll();
}
