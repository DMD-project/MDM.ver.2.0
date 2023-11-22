package ddwu.project.mdm_ver2.service.product;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import ddwu.project.mdm_ver2.contorller.product.ProductForm;
import ddwu.project.mdm_ver2.domain.Product;
@Component
public class ProductFormValidator  implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ProductForm.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("product form validator");

        ProductForm prodForm = (ProductForm)target;
        Product product = prodForm.getProduct();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product.name", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product.price", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product.content", "required");

        return;
    }
}
