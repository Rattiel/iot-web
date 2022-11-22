package com.greplfa.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.greplfa.web",
        "com.greplfa.iot.sdk",
        "com.greplfa.iot.sdk.device",
        "com.greplfa.iot.sdk.device.part",
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
