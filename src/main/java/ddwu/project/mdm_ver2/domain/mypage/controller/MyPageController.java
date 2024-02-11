package ddwu.project.mdm_ver2.domain.mypage.controller;

import ddwu.project.mdm_ver2.domain.favorite.entity.Favorite;
import ddwu.project.mdm_ver2.domain.favorite.service.FavoriteService;
import ddwu.project.mdm_ver2.domain.mypage.service.MyPageService;
import ddwu.project.mdm_ver2.domain.review.entity.Review;
import ddwu.project.mdm_ver2.domain.review.service.ReviewService;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/mypage")
public class MyPageController implements MyPageApi{

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final MyPageService myPageService;
    private final FavoriteService favoriteService;
    private final ReviewService reviewService;
    private final UserRepository userRepository;


    /* 닉네임 설정, 변경 */
//    @PostMapping("/mypage/nickname")
//    public CustomResponse<User> setUserNickname(Principal principal) {
//
//    }

    /* 닉네임 중복 확인 */
    @GetMapping("/check/{userNickname}")
    public boolean checkNickname(@PathVariable(value="userNickname", required=true) String nickname, Model model) {
        log.info("nickname: {}", nickname);
        log.info("nickname 중복 여부: {}", myPageService.checkNicknameDup(nickname)); // 중복 -> true, 중복X -> false
        model.addAttribute("nicknameDup", myPageService.checkNicknameDup(nickname));
        return myPageService.checkNicknameDup(nickname);
    }

    /* 주소 설정, 변경 */
//    @PostMapping("/mypage/address") 
//    public CustomResponse<User> setUserAddr(Principal principal) {
//        
//    }

    /* 사용자 찜 리스트 */
    @GetMapping("/favorite")
    public CustomResponse<List<Favorite>> getUserFavorite(Principal principal) {
        return favoriteService.getUserFavoriteList(principal.getName());
    }

    /* 사용자 작성 리뷰 리스트 */
    @GetMapping("/review")
    public CustomResponse<List<Review>> getUserReview(Principal principal) {
        return reviewService.getUserReviewList(principal.getName());
    }
}
