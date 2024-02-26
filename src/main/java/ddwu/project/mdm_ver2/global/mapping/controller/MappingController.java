package ddwu.project.mdm_ver2.global.mapping.controller;

import ddwu.project.mdm_ver2.domain.cart.service.CartService;
import ddwu.project.mdm_ver2.domain.grouppurchase.entity.GroupPurchaseParticipant;
import ddwu.project.mdm_ver2.domain.grouppurchase.service.GroupPurchaseService;
import ddwu.project.mdm_ver2.domain.product.service.ProductService;
import ddwu.project.mdm_ver2.domain.secondhand.service.SecondHandService;
import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
public class MappingController {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final GroupPurchaseService groupPurchaseService;
    private final SecondHandService secondHandService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String reqLogin() {
        return "login";
    }

    @GetMapping("/kakao")
    public String login(@RequestParam String code, Model model, HttpServletResponse response) {

        String access_token = userService.getAccessToken(code);

        HashMap<String, Object> userInfo = userService.getKakaoUserInfo(access_token);

        UserResponse userResponse = userService.checkKakaoUser(userInfo);

        JwtToken jwtToken = userService.setToken(userResponse, access_token);

        Cookie accessCookie = new Cookie("access_token", jwtToken.getAccess_token());
        accessCookie.setMaxAge(30 * 60); // 30분
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("refresh_token", jwtToken.getRefresh_token());
        refreshCookie.setMaxAge(14 * 24 * 60 * 60); // 2주
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);

        userService.addUser(userResponse);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logut";
    }

    @GetMapping("/product/list/view")
    public String getProductList() {
        return "product";
    }

    @GetMapping("/product/{prodId}/view")
    public String getProductDetail() {
        return "productDetails";
    }

    @GetMapping("/cart/view")
    public String getCart() {
        return "cart";
    }

    @GetMapping("/gp/list/view")
    public String getGroupPurchaseList() {
        return "grouppurchase";
    }

    @GetMapping("/gp/{gpId}/view")
    public String getGroupPurchaseDetail(){
        return "grouppurchaseDetails";
    }

    @GetMapping("/secondhand/list/view")
    public String getSecondHandList() {
        return "secondhand";
    }

    @GetMapping("/secondhand/{shId}/view")
    public String getSecondHandDetail() {
        return "secondhandDetails";
    }

    @GetMapping("/secondhand/add/view")
    public String addSecondHand() {
        return "addSecondhand";
    }

    @GetMapping("/mypage/view")
    public String getMypage() {
        return "mypage";
    }
}
