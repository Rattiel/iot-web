package com.greplfa.iot.sdk;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserMethod {
    private final RestTemplate rt = new RestTemplate();

    public void register(String userId, String nickname) {
        /*

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("user-uuid", testUsername);
        params.add("user-name", nickname);
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<?> response = rt.postForEntity(
                "http://iot.grepfa.com:8765/user/add", //{요청할 서버 주소}
                entity, // {요청할 때 보낼 데이터},
                Void.class
        );
         */
    }

    public void delete(String userId) {

    }
}


