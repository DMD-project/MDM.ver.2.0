package ddwu.project.mdm_ver2.service.product;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import ddwu.project.mdm_ver2.domain.Product;
@Component
public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("product validator");

        Product product = (Product)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product.name", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product.price", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product.content", "required");

        return;
    }
}
