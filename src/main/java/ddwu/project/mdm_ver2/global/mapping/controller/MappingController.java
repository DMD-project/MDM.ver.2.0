package ddwu.project.mdm_ver2.global.mapping.controller;

import ddwu.project.mdm_ver2.domain.user.dto.GeneralRequest;
import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@AllArgsConstructor
public class MappingController {

    private final UserService userService;

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

    @PostMapping("/signup")
    public String generalSignup(@RequestBody GeneralRequest request, HttpServletResponse response) {
        JwtToken token = userService.generalSignup(request.getEmail(), request.getPassword(), response);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String generalLogin(@RequestBody GeneralRequest request, HttpServletResponse response) {
        JwtToken token = userService.generalLogin(request.getEmail(), request.getPassword(), response);
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

    @GetMapping("/order/view")
    public String getOrderDetail() { return "order";}

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

    @GetMapping("/mypage/review/view")
    public String getReviewByUser() {
        return "mypage_review";
    }

    @GetMapping("/mypage/gp/view")
    public String getGroupPurchasesByUser() {
        return "mypage_gp";
    }

    @GetMapping("/mypage/sh/view")
    public String getSecondHandByUser() {
        return "mypage_sh";
    }

    @GetMapping("/mypage/shBid/view")
    public String getSecondHandBidByUser() {
        return "mypage_shBid";
    }

    @GetMapping("/mypage/faq/view")
    public String getFAQ() {
        return "mypage_FAQ";
    }

    @GetMapping("/order/finish/view")
    public String orderFinish() {
        return "order_finish";
    }
}
