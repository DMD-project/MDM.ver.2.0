package ddwu.project.mdm_ver2.contorller;

import ddwu.project.mdm_ver2.domain.Favorite;
import ddwu.project.mdm_ver2.domain.Product;
import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.repository.UserRepository;
import ddwu.project.mdm_ver2.service.FavoriteService;
import ddwu.project.mdm_ver2.service.KakaoService;
import ddwu.project.mdm_ver2.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/product/{id}")
public class FavoriteController {

    private FavoriteService fs;
    private KakaoService ks;
    private ProductService ps;

    @PostMapping("/test/fav") // 수정 ..
    public boolean getFavState(@RequestParam(value="kakaoEmail", required=true) String kakaoEmail,
//            Principal principal,
                               @PathVariable(value="id", required=true) long prodID) {
        User user = ks.getUser(kakaoEmail);
        Product product = ps.findProduct(prodID);
//        System.out.println(Long.valueOf(principal.getName()));
        return fs.getFavoriteState(user, product); // true(exist)-> 찜 상태, false -> 찜 아닌 상태
    }

    @GetMapping("/favState/{favState}")
    public Favorite changeFavState(@RequestParam(value="kakaoEmail", required=true) String kakaoEmail,
//            Principal principal,
                                   @PathVariable(value="id", required=true) long prodID,
                                   @PathVariable(value="favState", required = true) Character favState) {

        User user = ks.getUser(kakaoEmail);
        Product product = ps.findProduct(prodID);

        if(favState.equals('y')) { // 클릭 시 'n' -> 찜 해제(db 삭제)
            fs.deleteFavorite(user, product);
            return null;
        } else { // 클릭 시 'y' -> 찜 등록(db 추가)
//            return null;
            Favorite favorite = new Favorite(null, user, product, 'y');
            return fs.addFavorite(favorite);
        }
    }

}
