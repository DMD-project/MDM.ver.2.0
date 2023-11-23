package ddwu.project.mdm_ver2.service.product;

import java.util.List;

import ddwu.project.mdm_ver2.domain.Product;


public interface ProductFacade {
    void addProduct(Product product);

    void updateProduct(Product product);

    List<Product> getProductListByCategory(String cateCode);

    List<Product> searchProductList(String keywords);

    List<Product> findAll();

    Product getProduct(int productId) throws Exception;

    void deleteById(int id);

}
