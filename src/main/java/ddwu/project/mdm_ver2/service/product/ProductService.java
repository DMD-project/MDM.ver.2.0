package ddwu.project.mdm_ver2.service.product;

import ddwu.project.mdm_ver2.dao.ProductDao;
import ddwu.project.mdm_ver2.domain.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements ProductFacade {
    @PersistenceContext
    private EntityManager em;

    private ProductDao prodDao;

    @Autowired
    public ProductService(ProductDao prodDao) {
        this.prodDao = prodDao;
    }

    public void setProdDao(ProductDao prodDao) {
        this.prodDao = prodDao;
    }

    @Override
    public void addProduct(Product product) {
        prodDao.save(product);
    }


    @Override
    public void updateProduct(Product product) {
        em.merge(product);
    }

    @Override
    public List<Product> searchProductList(String keywords) {
        return prodDao.findByNameContaining(keywords);
    }


    @Override
    public List<Product> findAll() {
        return (List<Product>) prodDao.findAll();
    }


    @Override
    public Product getProduct(int productId) throws Exception {
        Optional<Product> result = prodDao.findById(productId);
        if (result.isPresent()) {
            Product product = result.get();
            return product;
        } else {
            throw new Exception();
        }

    }


    @Override
    public List<Product> getProductListByCategory(int cateCode) {
        return prodDao.findByCateID(cateCode);
    }


    @Override
    public void deleteById(int id) {
        Optional<Product> result = prodDao.findById(id);
        if (result.isPresent()) {
            prodDao.delete(result.get());
        }
    }
}
