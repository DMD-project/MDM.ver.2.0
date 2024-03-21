package ddwu.project.mdm_ver2.domain.user.controller;

import ddwu.project.mdm_ver2.domain.user.dto.GeneralRequest;
import ddwu.project.mdm_ver2.domain.user.service.UserService;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class GeneralController {
    private final UserService userService;

    @PostMapping("/ios/signup")
    public CustomResponse<JwtToken> iOSgeneralSignup(@RequestBody GeneralRequest request) {
        JwtToken token = userService.iOSgeneralSignup(request.getEmail(), request.getPassword());
        return CustomResponse.onSuccess(token);
    }

    @PostMapping("/ios/login")
    public CustomResponse<JwtToken> iOSgeneralLogin(@RequestParam String email, @RequestParam String password) {
        JwtToken token = userService.iOSgeneralLogin(email, password);
        return CustomResponse.onSuccess(token);
    }
}
