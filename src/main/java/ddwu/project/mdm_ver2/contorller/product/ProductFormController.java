package ddwu.project.mdm_ver2.contorller.product;

import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.service.product.ProductFacade;
import ddwu.project.mdm_ver2.service.product.ProductFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/shop/addProduct")
@SessionAttributes("prodReq")
public class ProductFormController {
    @Autowired
    private ProductFacade facade;

    public void setFacade(ProductFacade facade) {
        this.facade = facade;
    }

    @Autowired
    private ProductFormValidator validator;

    public void setValidator(ProductFormValidator validator) {
        this.validator = validator;
    }


    @ModelAttribute("prodReq")
    public ProductForm formBacking(HttpServletRequest request) {
        ProductForm productForm = new ProductForm();
        return productForm;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String form() {
        return "product/AddProductForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(HttpServletRequest request, HttpSession session,
                         @ModelAttribute("prodReq") ProductForm productForm, BindingResult result, SessionStatus status) throws Exception {

        System.out.println("productFormController submit");
        System.out.println(productForm.getProduct().toString());

        Product newProduct = productForm.getProduct();
        newProduct.setProdIMGUrl("../../../../images/cup.png");

        //save
        facade.addProduct(newProduct);

        return "redirect:/shop/shopMain";
    }
}
