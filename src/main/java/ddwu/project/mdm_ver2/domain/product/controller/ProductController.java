package ddwu.project.mdm_ver2.domain.product.controller;

import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.product.dto.ProductRequest;
import ddwu.project.mdm_ver2.domain.product.service.ProductService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController { //상품은 관리자만 담당

    private final ProductService productService;

    // 상품 조회
    @GetMapping("/list")
    public CustomResponse<List<Product>> getBagList(Principal principal) {
        try {
            List<Product> productList = productService.findAllProduct();
            return CustomResponse.onSuccess(productList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 상품 조회 / 상품 정렬 - 가격순(lowprice,highprice), 등록순(newest)
    @GetMapping("/sort/category")
    public CustomResponse<List<Product>> getSortedList(
            @RequestParam(name = "sortBy", required = false, defaultValue = "") String sortBy,
            @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode) {
        try {
            List<Product> sortedProductList = productService.SortProduct(sortBy, cateCode);
            return CustomResponse.onSuccess(sortedProductList);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    //상품 개별 조회
    @GetMapping("/{id}")
    public CustomResponse<Product> getProduct(@PathVariable("id") Long id) {
        try {
            Product product = productService.findProduct(id);
            return CustomResponse.onSuccess(product);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    //상품 추가
    @PostMapping("/add")
    public CustomResponse<Product> addProduct(@RequestBody ProductRequest request) {
        try {
            Product addedProduct = productService.addProduct(request);
            return CustomResponse.onSuccess(addedProduct);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    //상품 수정
    @PutMapping("update/{id}")
    public CustomResponse<Product> updateProduct(@RequestBody ProductRequest request, @PathVariable("id") Long id) {
        try {
            Product updatedProduct = productService.updateProduct(id, request);
            return CustomResponse.onSuccess(updatedProduct);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    //상품 삭제
    @DeleteMapping("delete/{id}")
    public CustomResponse<Void> deleteBag(@PathVariable("id") Long id) {
        try {
            productService.deleteProduct(id);
            return CustomResponse.onSuccess(null);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 상품 개수 조회
    @GetMapping("/count")
    public CustomResponse<Long> getProductCount(
            @RequestParam(name = "cateCode", required = false, defaultValue = "") String cateCode) {
        try {
            long count = cateCode.isEmpty() ? productService.getProductCount() : productService.getProductCountByCategory(cateCode);
            return CustomResponse.onSuccess(count);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    // 상품 검색
    @GetMapping("/search/{keyword}")
    public CustomResponse<List<Product>> searchProduct(@RequestParam(name = "keyword") String keyword) {
        try {
            List<Product> searchResults = productService.searchProduct(keyword);
            return CustomResponse.onSuccess(searchResults);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

}
