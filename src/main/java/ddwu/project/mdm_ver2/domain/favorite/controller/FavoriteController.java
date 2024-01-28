//package ddwu.project.mdm_ver2.domain.favorite.controller;
//
//import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
//import ddwu.project.mdm_ver2.domain.product.entity.Product;
//import ddwu.project.mdm_ver2.domain.user.entity.User;
//import ddwu.project.mdm_ver2.domain.favorite.service.FavoriteService;
//import ddwu.project.mdm_ver2.domain.user.service.UserService;
//import ddwu.project.mdm_ver2.domain.product.service.ProductService;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/product/{id}")
//public class FavoriteController {
//
//    private FavoriteService fs;
//    private UserService ks;
//    private ProductService ps;
//
//    @PostMapping("/test/fav") // 수정 ..
//    public boolean getFavState(@RequestParam(value="kakaoEmail", required=true) String kakaoEmail,
////            Principal principal,
//                               @PathVariable(value="id", required=true) long prodID) {
//        User user = ks.getUser(kakaoEmail);
//        Product product = ps.findProduct(prodID);
////        System.out.println(Long.valueOf(principal.getName()));
//        return fs.getFavoriteState(user, product); // true(exist)-> 찜 상태, false -> 찜 아닌 상태
//    }
//
//    @GetMapping("/favState/{favState}")
//    public Favorite changeFavState(@RequestParam(value="kakaoEmail", required=true) String kakaoEmail,
////            Principal principal,
//                                   @PathVariable(value="id", required=true) long prodID,
//                                   @PathVariable(value="favState", required = true) Character favState) {
//
//        User user = ks.getUser(kakaoEmail);
//        Product product = ps.findProduct(prodID);
//        System.out.println(user.getKakaoEmail());
//        if(favState.equals('y')) { // 클릭 시 'n' -> 찜 해제(db 삭제)
//            fs.deleteFavorite(user, product);
//            return null;
//        } else { // 클릭 시 'y' -> 찜 등록(db 추가)
////            return null;
//            Favorite favorite = new Favorite(user, product, 'y');
//            return fs.addFavorite(favorite);
//        }
//    }
//
//}
