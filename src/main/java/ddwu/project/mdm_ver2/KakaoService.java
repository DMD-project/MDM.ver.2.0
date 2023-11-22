package ddwu.project.mdm_ver2;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
public class KakaoService {

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

    public HashMap<String, Object> getKakaoUserInfo(String token) {

        HashMap<String, Object> userInfo = new HashMap<>();

        String request_url = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(request_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); // request header setting

            // success: code = 200
            int responseCode = conn.getResponseCode();
            System.out.println("response code: " + responseCode);

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

            int id = element.getAsJsonObject().get("id").getAsInt();
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            String profileIMG = properties.getAsJsonObject().get("profile_image").getAsString();

            userInfo.put("userID", id);
            userInfo.put("profileIMG", profileIMG);

            /*
            response body: {
                "id":3160989952,
                "connected_at":"2023-11-13T08:15:41Z",
                "properties":{
                    "profile_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
                    "thumbnail_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg"},
                    "kakao_account":{
                        "profile_image_needs_agreement":false,
                        "profile":{
                            "thumbnail_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg",
                            "profile_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
                            "is_default_image":true}}}
            */

            System.out.println("id: " + id);
            System.out.println("profileIMG: " + profileIMG);

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }
}
