package ddwu.project.mdm_ver2.contorller.product;

import ddwu.project.mdm_ver2.service.product.ProductFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Product")
@RestController
@RequestMapping("/product/delete")
public class DeleteProductController {
    private ProductFacade facade;

    @Autowired
    public void setFacade(ProductFacade facade) {
        this.facade = facade;
    }

    @Operation(summary = "상품 삭제")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@RequestParam("id") int id) throws Exception {
        facade.deleteById(id);

        return ResponseEntity.ok().body("삭제가 완료되었습니다.");
    }
}
