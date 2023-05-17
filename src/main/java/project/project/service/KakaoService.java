package project.project.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class KakaoService {

    public String getKakaoData(String code){
        String accessToken="";
        String refreshToken="";
        String requestUrl="https://kauth.kakao.com/oauth/token";
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값인 false인 setDoOutput을 true 변경
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuffer sb = new StringBuffer();

            sb.append("grant_type=authorization_code");
            sb.append("&client_id=e380cd44828b9940dbcefe3517d0fb69"); // REST API 키
            sb.append("&redirect_uri=http://localhost:7080")

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
