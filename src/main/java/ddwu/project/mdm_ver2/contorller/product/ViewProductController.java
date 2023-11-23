package ddwu.project.mdm_ver2.contorller.product;

import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.service.product.ProductFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Product")
@RestController
@RequestMapping("/shop/product")
public class ViewProductController {
    private ProductFacade facade;

    @Autowired
    public void setProductFacade(ProductFacade facade) {
        this.facade = facade;
    }

    @Operation(summary = "상품 보기")
    @RequestMapping(method = RequestMethod.GET)
    public Product viewProd(@RequestParam("prodId") int prodId,
                           ModelMap model) throws Exception {

        Product product = this.facade.getProduct(prodId);
        return this.facade.getProduct(prodId);
    }
}
