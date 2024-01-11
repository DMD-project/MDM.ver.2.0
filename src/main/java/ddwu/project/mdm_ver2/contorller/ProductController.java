package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.dto.ProductRequest;
import ddwu.project.mdm_ver2.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")  // 모든 매핑에 "/product"를 추가
public class ProductController { //상품은 관리자만 담당

    private final ProductService productService;

    // 상품 조회
    @GetMapping("/list")
    public List<Product> getBagList() {
        return productService.findAllProduct();
    }

    @GetMapping("/{id}")
    public Product getBag(@PathVariable("id") Long id) {
        return productService.findProduct(id);
    }

    //상품 추가
    @PostMapping
    public Product addProduct(@RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }

    //상품 수정
    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable("id") Long id) {
        return productService.updateProduct(id, product);
    }

    //상품 삭제
    @DeleteMapping("/{id}")
    public void deleteBag(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }
}
