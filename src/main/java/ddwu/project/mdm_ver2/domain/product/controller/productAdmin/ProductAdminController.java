package ddwu.project.mdm_ver2.domain.product.controller.productAdmin;

import ddwu.project.mdm_ver2.domain.product.dto.ProductRequest;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.product.service.ProductService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/product")
public class ProductAdminController implements ProductAdminApi {

    private final ProductService productService;

    /* 상품 추가 */
    @PostMapping("/add")
    public CustomResponse<Void> addProduct(@RequestBody ProductRequest request) {
        return productService.addProduct(request);
    }

    /* 상품 수정 */
    @PutMapping("update/{prodId}")
    public CustomResponse<Void> updateProduct(@RequestBody ProductRequest request, @PathVariable("prodId") Long prodId) {
        return productService.updateProduct(prodId, request);
    }

    /* 상품 삭제 */
    @DeleteMapping("delete/{prodId}")
    public CustomResponse<Void> deleteBag(@PathVariable("prodId") Long prodId) {
        return productService.deleteProduct(prodId);
    }
}