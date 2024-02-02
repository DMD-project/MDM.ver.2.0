package ddwu.project.mdm_ver2.domain.favorite.controller;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.product.entity.Product;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.favorite.service.FavoriteService;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import ddwu.project.mdm_ver2.domain.product.service.ProductService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/product/{prodId}")
public class FavoriteController {

    private FavoriteService favoriteService;
    private UserService userService;
    private ProductRepository productRepository;

    @GetMapping("/favState/{favState}")
    public CustomResponse<Favorite> changeFavoriteState(@RequestParam(name = "userEmail", required = true) String userEmail,
//            Principal principal,
                                                   @PathVariable(value="prodId", required=true) long prodId,
                                                   @PathVariable(value="favState", required = true) Character favState) {
//        return favoriteService.setFavoriteState(principal.getName(), prodId, favState);
        return favoriteService.setFavoriteState(userEmail, prodId, favState);
    }

}
