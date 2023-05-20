package project.project.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.valves.JsonAccessLogValve;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.project.domain.User;
import project.project.domain.enum_type.UserJoinType;
import project.project.repository.UserRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final UserRepository userRepository;

    private final String GRANT_TYPE = "authorization_code";
    private final String CLIENT_ID = "e380cd44828b9940dbcefe3517d0fb69";
    private final String REDIRECT_URI = "http://localhost:7080/kakao/login";
    private final String CLIENT_SECRET="TDoc2ffICcdms6XtnHXgtHwKqKRa4lMg";
    private final String TOKEN_URI="https://kauth.kakao.com/oauth/token";


    private final String INFO_REQUEST="https://kapi.kakao.com/v2/user/me";

    public User kakaoLogin(String code){
        String kakaoToken = getKakaoToken(code);
        String userInfo = getUserInfo(new JSONObject(kakaoToken).getString("access_token"));

        JSONObject jsonObject = new JSONObject(userInfo);
        String id = jsonObject.get("id").toString();

        Optional<User> findUser = userRepository.findByKakaoId(id);

        if(findUser.isEmpty()){
            JSONObject kakao_account = (JSONObject) jsonObject.get("kakao_account");
            User user = new User(kakao_account.has("email") ? kakao_account.getString("email") : null,
                    kakao_account.has("birthday") ? kakao_account.getString("birthday") : null,
                    kakao_account.has("profile") ? kakao_account.getJSONObject("profile").getString("profile_image_url") : null,
                    id,
                    UserJoinType.KAKAO);
            userRepository.save(user);
            return user;
        }

        return findUser.get();

    }
    public String getKakaoToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        //header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity request = new HttpEntity(headers);

        //URI 빌더 사용
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(TOKEN_URI)
                .queryParam("grant_type", GRANT_TYPE)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("code", code)
                .queryParam("client_secret", CLIENT_SECRET);

        ResponseEntity<String> response = restTemplate.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.POST,
                request,
                String.class
        );


        if(response.getStatusCode()==HttpStatus.OK){
            return response.getBody();
        }
        return "error";


    }

    public String getUserInfo(String token){
        String jsonData="";

        try {
            URL url = new URL(INFO_REQUEST + "?access_token=" + token);

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String line;
            while ((line=br.readLine())!=null){
                jsonData+=line;
            }

            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
            return "err";
        }
    }
}
