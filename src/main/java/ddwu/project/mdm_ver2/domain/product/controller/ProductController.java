package ddwu.project.mdm_ver2.domain.product.controller;

import ddwu.project.mdm_ver2.domain.product.controller.productAdmin.ProductAdminApi;
import ddwu.project.mdm_ver2.domain.product.dto.ProductResponse;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.product.service.ProductService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController implements ProductApi { //상품은 관리자만 담당

    private final ProductService productService;

    // 상품 조회
    @GetMapping("/list")
    public CustomResponse<List<Product>> getProductList() {
        return productService.findAllProduct();
    }

    // 상품 정렬
    @GetMapping("/sort/category")
    public CustomResponse<List<Product>> getSortedList(@RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy, @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode) {
        return productService.SortProduct(sortBy, cateCode);
    }

    //상품 개별 조회
    @GetMapping("/{prodId}")
    public CustomResponse<ProductResponse> getProduct(Principal principal, @PathVariable("prodId") Long prodId) {
        return productService.getProduct(principal, prodId);
    }

    // 상품 개수 조회
    @GetMapping("/count")
    public CustomResponse<Long> getProductCount(@RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode) {
        return cateCode.isEmpty() ? productService.getProductCount() : productService.getProductCountByCategory(cateCode);
    }

    // 상품 검색
    @GetMapping("/search/{keyword}")
    public CustomResponse<List<Product>> searchProduct(@RequestParam(name = "keyword") String keyword) {
        return productService.searchProduct(keyword);
    }

}
