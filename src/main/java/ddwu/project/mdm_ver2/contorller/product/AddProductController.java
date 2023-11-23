package ddwu.project.mdm_ver2.contorller.product;

import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.service.product.ProductFacade;
import ddwu.project.mdm_ver2.service.product.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Product")
@RestController
@RequestMapping("/shop/addProduct")
public class AddProductController {
    private final ProductFacade facade;
    private final ProductValidator validator;

    @Autowired
    public AddProductController(ProductFacade facade, ProductValidator validator) {
        this.facade = facade;
        this.validator = validator;
    }

    @Operation(summary = "상품 추가")
    @RequestMapping(method = RequestMethod.POST)
    public Product submit(@RequestBody Product product, BindingResult result) throws Exception {

        facade.addProduct(product);     //save
        return product;
    }
}
