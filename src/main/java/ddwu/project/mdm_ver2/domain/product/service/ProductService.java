package ddwu.project.mdm_ver2.domain.product.service;

import ddwu.project.mdm_ver2.domain.category.entity.Category;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.product.dto.ProductRequest;
import ddwu.project.mdm_ver2.global.exception.ResourceNotFoundException;
import ddwu.project.mdm_ver2.domain.category.repository.CategoryRepository;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collections;
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

    // 상품 정렬
    @Transactional
    public List<Product> SortProduct(String sort, String cateCode) {
        if (cateCode != null && !cateCode.isEmpty()) {
            return sortProductsByCategory(sort, cateCode);
        }

        switch (sort) {
            case "lowprice":
                return productRepository.findAllByOrderByPriceAsc();
            case "highprice":
                return productRepository.findAllByOrderByPriceDesc();
            case "newest":
                return productRepository.findAllByOrderByIdDesc();
            default:
                return productRepository.findAll();
        }
    }

    // 카테고리 분류 후 상품 정렬
    @Transactional
    public List<Product> sortProductsByCategory(String sort, String cateCode) {
        List<Product> productList;

        switch (sort) {
            case "lowprice":
                productList = productRepository.findAllByCategoryCateCodeOrderByPriceAsc(cateCode);
                break;
            case "highprice":
                productList = productRepository.findAllByCategoryCateCodeOrderByPriceDesc(cateCode);
                break;
            case "newest":
                productList = productRepository.findAllByCategoryCateCodeOrderByIdDesc(cateCode);
                break;
            default:
                productList = productRepository.findAllByCategoryCateCode(cateCode);
                break;
        }

        return productList;
    }


    private List<Product> categorySort(String category, List<Product> defaultList, List<Product> categoryList) {
        return StringUtils.isNotBlank(category) ? categoryList : defaultList;
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

        if (category == null) {
            throw new NotFoundException("카테고리를 찾을 수 없습니다: " + request.getCategory());
        }

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

    //상품 개수 - 전체
    public long getProductCount() {
        return productRepository.count();
    }

    //상품 개수 - 카테고리 분류
    public long getProductCountByCategory(String cateCode) {
        return productRepository.countByCategoryCateCode(cateCode);
    }

    // 상품 검색
    @Transactional
    public List<Product> searchProduct(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return Collections.emptyList();
        }

        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

}
