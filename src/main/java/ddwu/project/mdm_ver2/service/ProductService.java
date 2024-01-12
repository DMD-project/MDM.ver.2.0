package ddwu.project.mdm_ver2.service;

import ddwu.project.mdm_ver2.domain.Category;
import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.dto.ProductRequest;
import ddwu.project.mdm_ver2.exception.ResourceNotFoundException;
import ddwu.project.mdm_ver2.repository.CategoryRepository;
import ddwu.project.mdm_ver2.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    //상품 조회
    @Transactional
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    //상품 개별 조회
    @Transactional
    public Product findProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    //상품 추가
    @Transactional
    public Product addProduct(ProductRequest request) {

        Category category = categoryRepository.findByCateCode(request.getCategory());

        Product product = Product.builder()
                .category(category)
                .name(request.getName())
                .price(request.getPrice())
                .content(request.getContent())
                .prodIMGUrl(request.getProdIMGUrl())
                .build();

        return productRepository.save(product);
    }
    //상품 수정
    @Transactional
    public Product updateProduct(Long id, ProductRequest updatedProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        if (product != null) {
            Category category = categoryRepository.findByCateCode(updatedProduct.getCategory());

            product.setCategory(category);
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setContent(updatedProduct.getContent());
            product.setProdIMGUrl(updatedProduct.getProdIMGUrl());

            return productRepository.save(product);
        }

        return null;
    }

    //상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        productRepository.delete(product);
    }
}
