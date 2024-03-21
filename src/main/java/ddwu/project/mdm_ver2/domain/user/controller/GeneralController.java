package ddwu.project.mdm_ver2.domain.user.controller;

import ddwu.project.mdm_ver2.domain.user.dto.GeneralRequest;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/general")
public class GeneralController {

    private final UserService userService;

    @PostMapping("/signup")
    public CustomResponse<JwtToken> generalSignup(@RequestBody GeneralRequest request, HttpServletResponse response) {
        JwtToken token = userService.generalSignup(request.getEmail(), request.getPassword(), response);
        return CustomResponse.onSuccess(token);
    }

    @PostMapping("/login")
    public CustomResponse<JwtToken> generalLogin(@RequestBody GeneralRequest request, HttpServletResponse response) {
//        System.out.println(email + " " + password);
        JwtToken token = userService.generalLogin(request.getEmail(), request.getPassword(), response);
        return CustomResponse.onSuccess(token);
    }

    @PostMapping("/ios/signup")
    public CustomResponse<JwtToken> iosGeneralSignup(@RequestBody GeneralRequest request) {
        JwtToken token = userService.iosGeneralSignup(request.getEmail(), request.getPassword());
        return CustomResponse.onSuccess(token);
    }

    @PostMapping("/ios/login")
    public CustomResponse<JwtToken> iosGeneralLogin(@RequestParam String email, @RequestParam String password) {
        JwtToken token = userService.iosGeneralLogin(email, password);
        return CustomResponse.onSuccess(token);
    }
}