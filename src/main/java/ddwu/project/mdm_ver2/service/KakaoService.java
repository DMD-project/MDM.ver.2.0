package ddwu.project.mdm_ver2.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ddwu.project.mdm_ver2.domain.User;
import ddwu.project.mdm_ver2.dto.UserDTO;
import ddwu.project.mdm_ver2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class KakaoService {

    private UserRepository userRepository;

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
            sb.append("&client_id=bbb0d5e603062dd02da05a9fe89b0c1e"); // REST_API_KEY
            sb.append("&redirect_uri=http://localhost:8080/kakao"); // redirect uri
            sb.append("&code=" +code);

            bw.write(sb.toString());
            bw.flush();

            // success: code = 200
            int responseCode = conn.getResponseCode();
            System.out.println("response code: " +responseCode);

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

            System.out.println("access token: " +access_token);
            System.out.println("refresh token: " +refresh_token);

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

            long userCode = element.getAsJsonObject().get("id").getAsLong();
            String kakaoEmail = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String kakaoProfileImg = properties.getAsJsonObject().get("profile_image").getAsString();

            userInfo.put("userCode", userCode);
            userInfo.put("kakaoEmail", kakaoEmail);
            userInfo.put("kakaoProfileImg", kakaoProfileImg);


            System.out.println("userCode: " + userCode);
            System.out.println("kakaoEmail: " + kakaoEmail);
            System.out.println("kakaoProfileImg: " + kakaoProfileImg);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public boolean checkKakaoUser(UserDTO userInfo) {

        System.out.println(userInfo.getKakaoEmail());

        boolean userExist = existUser(userInfo.getUserCode());

        if(!userExist) {
            System.out.println("User is NOT exist\nsaving ...");
            addUser(userInfo);
            return false;
        }

        System.out.println("User is already EXIST !");
        return true;
    }

    public void logout(String access_token) {
        String request_url = "https://kapi.kakao.com/v1/user/logout";

        try {
            URL url = new URL(request_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + access_token); // request header setting

            // success: code = 200
            int responseCode = conn.getResponseCode();
            System.out.println("response code: " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("response body: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User setUserNickname(String kakaoEmail, String nickname) {
        User user = userRepository.findByKakaoEmail(kakaoEmail);
        user.setUserNickname(nickname);
        return userRepository.saveAndFlush(user);
    }

    public boolean checkNicknameDup(String nickname) {
        return userRepository.existsByUserNickname(nickname);
    }

//    public UserDTO getUser(String kakaoEmail) {
//        User user = userRepository.findByKakaoEmail(kakaoEmail);
//
//    }

    public User getUser(String kakaoEmail) {
        return userRepository.findByKakaoEmail(kakaoEmail);
    }

    public User addUser(UserDTO userdto) {
        return userRepository.saveAndFlush(userdto.toEntity());
    }

    public boolean existUser(long userCode) {
        return userRepository.existsByUserCode(userCode);
    }

    public void deleteUser(long userCode) {
        userRepository.deleteByUserCode(userCode);
    }
}
