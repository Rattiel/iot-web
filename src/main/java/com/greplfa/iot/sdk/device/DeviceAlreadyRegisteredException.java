package com.greplfa.iot.sdk.device;

public class DeviceAlreadyRegisteredException extends RuntimeException {
    public DeviceAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
