package com.greplfa.iot.sdk.device.part;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Builder
public class PartMethod {
    private String uuid;

    private String label;

    public void action(Object option, String userId) {
        RestTemplate rt = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ChannelId", uuid);
        params.add("Value", option.toString());

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        log.info("명령 수행(파츠 라벨 : " + label + ", option : " + option + ")");

        /*
        ResponseEntity<?> response = rt.postForEntity(
                "http://iot.grepfa.com:8765/set", //{요청할 서버 주소}
                entity, // {요청할 때 보낼 데이터},
                Void.class
        );

         */
    }
}
