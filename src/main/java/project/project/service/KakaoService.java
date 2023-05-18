package project.project.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class KakaoService {
    private final String GRANT_TYPE = "authorization_code";
    private final String CLIENT_ID = "e380cd44828b9940dbcefe3517d0fb69";
    private final String REDIRECT_URI = "http://localhost:7080/kakao/login";
    private final String CLIENT_SECRET="TDoc2ffICcdms6XtnHXgtHwKqKRa4lMg";
    private final String TOKEN_URI="https://kauth.kakao.com/oauth/token";


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
}
