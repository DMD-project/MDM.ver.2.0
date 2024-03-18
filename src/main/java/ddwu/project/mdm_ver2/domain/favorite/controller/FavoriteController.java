package ddwu.project.mdm_ver2.domain.favorite.controller;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.product.repository.ProductRepository;
import ddwu.project.mdm_ver2.domain.favorite.service.FavoriteService;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/favType/{favType}/typeId/{typeId}")
public class FavoriteController implements FavoriteApi{

    private FavoriteService favoriteService;

    @PostMapping("/favState/{favState}")
    public CustomResponse<Void> changeFavoriteState(Principal principal,
                                                    @PathVariable(value = "favType", required = true) String favType,
                                                    @PathVariable(value = "typeId", required = true) long typeId,
                                                    @PathVariable(value = "favState", required = true) Character favState) {
        return favoriteService.setFavoriteState(principal.getName(), favType, typeId, favState);
    }
}