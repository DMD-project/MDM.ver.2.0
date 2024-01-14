package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.dto.ProductRequest;
import ddwu.project.mdm_ver2.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController { //상품은 관리자만 담당

    private final ProductService productService;

    // 상품 조회
    @GetMapping("/list")
    public List<Product> getBagList() {
        return productService.findAllProduct();
    }

    // 상품 조회 / 상품 정렬 - 가격순(lowprice,highprice), 등록순(newest)
    @GetMapping("/sort")
    public List<Product> getSortedList(
            @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy,
            @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode) {
        return productService.SortProduct(sortBy, cateCode);
    }

    //상품 개별 조회
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
    public Product updateProduct(@RequestBody ProductRequest request, @PathVariable("id") Long id) {
        return productService.updateProduct(id, request);
    }

    //상품 삭제
    @DeleteMapping("/{id}")
    public void deleteBag(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }
}
