package ddwu.project.mdm_ver2.domain.user.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ddwu.project.mdm_ver2.domain.user.entity.Role;
import ddwu.project.mdm_ver2.domain.user.entity.User;
import ddwu.project.mdm_ver2.domain.user.dto.UserResponse;
import ddwu.project.mdm_ver2.domain.user.repository.UserRepository;
import ddwu.project.mdm_ver2.global.exception.CustomResponse;
import ddwu.project.mdm_ver2.global.jwt.JwtProvider;
import ddwu.project.mdm_ver2.global.jwt.JwtToken;
import ddwu.project.mdm_ver2.global.redis.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${kakao.rest-api-key}")
    private String restApiKey;

    @Value("${kakao.login-uri}")
    private String loginUri;

    @Value("${kakao.logout-uri}")
    private String logoutUri;

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RedisUtil redisUtil;

    public String getAccessToken(String code) {

        String access_token = "";
        String refresh_token = "";

        String request_url = "https://kauth.kakao.com/oauth/token";
        try {
            URL url = new URL(request_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에 필요한 parameter 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" +restApiKey); // REST_API_KEY
            sb.append("&redirect_uri=" +loginUri); // redirect uri
            sb.append("&code=" +code);

            bw.write(sb.toString());
            bw.flush();

            // success: code = 200
            int responseCode = conn.getResponseCode();
//            System.out.println("response code: " +responseCode);

            // 요청으로 얻은 JSON type Response message
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("response body: " +result);

            // JSON Parsing 객체 생성 (Gson 라이브러리에 포함된 클래스 사용)
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("kakao access token: " +access_token);
            br.close();
            bw.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

        return access_token;
    }

    public HashMap<String, Object> getKakaoUserInfo(String access_token) {

        HashMap<String, Object> userInfo = new HashMap<>();

        String request_url = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(request_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + access_token); // request header setting

            // success: code = 200
            int responseCode = conn.getResponseCode();

            // 요청으로 얻은 JSON type Response message
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("response body: " + result);

            // JSON Parsing 객체 생성 (Gson 라이브러리에 포함된 클래스 사용)
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            long userId = element.getAsJsonObject().get("id").getAsLong();
            String userEmail = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String userProfileImg = properties.getAsJsonObject().get("profile_image").getAsString();

            userInfo.put("userId", userId);
            userInfo.put("userEmail", userEmail);
            userInfo.put("userProfileImg", userProfileImg);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public UserResponse checkKakaoUser(HashMap<String, Object> userInfo) {

        boolean existUser = existUser(userInfo.get("userEmail").toString());

        UserResponse userResponse;

        if(!existUser) { // 신규회원
            String defaultNickname = setDefaultNickname(userInfo.get("userId").toString());
            userResponse = new UserResponse((long) userInfo.get("userId"), defaultNickname, userInfo.get("userEmail").toString(), userInfo.get("userProfileImg").toString(), Role.USER);
            System.out.println(userResponse.getRole());
        } else { // 기존회원
            userResponse = getUser(userInfo.get("userEmail").toString()).toDTO();
        }

        return userResponse;
    }

    public JwtToken setToken(UserResponse userResponse, String kakao_access_token) {
        String jwt_access = jwtProvider.createAccessToken(userResponse.getId(), kakao_access_token);
        String jwt_refresh = jwtProvider.createRefreshToken(userResponse.getId(), kakao_access_token);

        System.out.println("jwt_access: " +jwt_access);

        return new JwtToken(jwt_access, jwt_refresh);
    }

    public boolean existUser(String userEmail) {
        return userRepository.existsByEmail(userEmail);
    }

    public String setDefaultNickname(String userId) {
        String defaultNickname = "user" + userId.substring(5) + userId.substring(0, 5);
//        3277908747
        return defaultNickname;
    }

    public User getUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        return user;
    }

    public User addUser(UserResponse userResponse) {
        return userRepository.saveAndFlush(userResponse.toEntity());
    }

    public CustomResponse<JwtToken> reissue(HttpServletRequest request) {
        String refresh_token = request.getHeader("Authorization").split(" ")[1];

        if(!jwtProvider.isValidate(refresh_token)) {
            return CustomResponse.onFailure(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 Refresh Token입니다"); // 재로그인 필요
        }

        Long userId = jwtProvider.getUserId(refresh_token);
        String kakao_access_token = jwtProvider.getKakaoAccessToken(refresh_token);

        if(redisUtil.getData(String.valueOf(userId)) == null) { // 로그아웃되어 Redis에 존재 X
            return CustomResponse.onFailure(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.");
        }

        if(!refresh_token.equals(redisUtil.getData(String.valueOf(userId)))) {
            return CustomResponse.onFailure(HttpStatus.BAD_REQUEST.value(), "Refresh Token 정보가 일치하지 않습니다");
        }

        String new_access_token = jwtProvider.createAccessToken(userId, kakao_access_token);

        return CustomResponse.onSuccess(new JwtToken(new_access_token, refresh_token));
    }

    public CustomResponse<Void> logout(HttpServletRequest request) {
        String access_token = request.getHeader("Authorization").split(" ")[1];
        System.out.println("Access Token for Logout: " +access_token);

        if(!jwtProvider.isValidate(access_token)) {
            return CustomResponse.onFailure(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 Access Token입니다");
        }

        Long userId = jwtProvider.getUserId(access_token);

        String request_url = "https://kauth.kakao.com/oauth/logout?client_id="+restApiKey+"&logout_redirect_uri="+logoutUri;
        try {
            URL url = new URL(request_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            // success: code = 302
            int responseCode = conn.getResponseCode();
            System.out.println("response code: " + responseCode);

            if(redisUtil.getData(String.valueOf(userId)) != null) {
                redisUtil.deleteData(String.valueOf(userId)); // Redis에 있는 Refresh Token 삭제
            }

            Long expiration = jwtProvider.getAccessTokenValidTime(access_token);
            redisUtil.setDataExpire(access_token, "logout", expiration); // 해당 Access Token BlackList 등록

            System.out.println("로그아웃 성공");
            return CustomResponse.onSuccess(null);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "로그아웃에 실패하였습니다.");
        }
    }

    public CustomResponse<Void> deleteUser(HttpServletRequest request) {
        String access_token = request.getHeader("Authorization").split(" ")[1];
        System.out.println(access_token);

        String kakao_access_token = jwtProvider.getKakaoAccessToken(access_token);

        String request_url = "https://kapi.kakao.com/v1/user/unlink";
        try {
            URL url = new URL(request_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + kakao_access_token); // request header setting

            // success: code = 200
            int responseCode = conn.getResponseCode();
            System.out.println("response code: " +responseCode);

            // 요청으로 얻은 JSON type Response message
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("response body: " + result);

            // JSON Parsing 객체 생성 (Gson 라이브러리에 포함된 클래스 사용)
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            Long id = element.getAsJsonObject().get("id").getAsLong();
            System.out.println(id);

            br.close();

            redisUtil.deleteData(String.valueOf(id));
            userRepository.deleteById(id);

            return CustomResponse.onSuccess(null);
        } catch (Exception e) {
            return CustomResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원 탈퇴에 실패하였습니다.");
        }
    }
}
