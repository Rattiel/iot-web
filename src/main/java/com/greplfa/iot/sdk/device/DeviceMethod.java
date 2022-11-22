package com.greplfa.iot.sdk.device;

import com.greplfa.iot.sdk.device.part.PartInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeviceMethod {
    private final RestTemplate rt = new RestTemplate();

    private final static String DEVICE_UUID = "bec6f91b-9adc-40c7-98b5-3374195512ce";
    private final static String TEMP_UUID = "e31f2968-8222-40c6-8bd8-9e213d7ea74b";
    private final static String HUM_UUID = "ac6294f9-ef1b-47fc-8f8c-197748476540";
    private final static String CO2_UUID = "655c49e6-c7c5-4603-841c-ce31d1919ae3";
    private final static String BUTTON_UUID = "a2das2sd-c7c5-4603-841c-ce31d1919ae3";
    private final static String SLIDER_UUID = "as2sfg3d-c7c5-4603-841c-ce31d1919ae3";
    private final DeviceInfo deviceInfo;

    //TODO : 테스트 코드 -> 테스트 디바이스 등록 여부
    private boolean check = false;

    public DeviceMethod() {
        DeviceContent content = DeviceContent.builder()
                .deviceId(DEVICE_UUID)
                .deviceName("테스트 장비")
                .build();

        List<PartInfo> channels = new ArrayList<>();

        PartInfo tempPart = PartInfo.builder()
                .device(DEVICE_UUID)
                .channelId(TEMP_UUID)
                .channelType("float")
                .channelName("temp")
                .channelIsControl(false)
                .build();
        channels.add(tempPart);

        PartInfo humPart = PartInfo.builder()
                .device(DEVICE_UUID)
                .channelId(HUM_UUID)
                .channelType("float")
                .channelName("hum")
                .channelIsControl(false)
                .build();
        channels.add(humPart);

        PartInfo co2Part = PartInfo.builder()
                .device(DEVICE_UUID)
                .channelId(CO2_UUID)
                .channelType("float")
                .channelName("co2")
                .channelIsControl(false)
                .build();
        channels.add(co2Part);

        PartInfo buttonPart = PartInfo.builder()
                .device(DEVICE_UUID)
                .channelId(BUTTON_UUID)
                .channelType("boolean")
                .channelName("버튼")
                .channelIsControl(true)
                .build();
        channels.add(buttonPart);

        PartInfo sliderPart = PartInfo.builder()
                .device(DEVICE_UUID)
                .channelId(SLIDER_UUID)
                .channelType("int")
                .channelName("슬라이더")
                .channelIsControl(true)
                .build();
        channels.add(sliderPart);

        deviceInfo = DeviceInfo.builder()
                .content(content)
                .channels(channels)
                .build();
    }

    public DeviceInfo register(String deviceUuid, String userId) {
        if (check) {
            throw new DeviceAlreadyRegisteredException("테스트 장비 이미 등록 됌.");
        }

        //TODO : iot 서버 미구현(추후 사용)
        /*
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("device-uuid", deviceUuid);
        params.add("user-uui", userUuid);
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<?> response = rt.postForEntity(
                "http://iot.grepfa.com:8765/device/add", //{요청할 서버 주소}
                entity, // {요청할 때 보낼 데이터},
                Void.class
        );
         */

        check = true;
        return deviceInfo;
    }

    public void delete(String uuid, UserDetails userDetails) {
        if (!check) {
            throw new DeviceNotRegisteredException("테스트 장비 등록 안됌.");
        }
        // ignore;
    }
}

// https://semtax.tistory.com/83