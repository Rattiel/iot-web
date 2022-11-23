package com.greplfa.web.domain.iot.device.service;

import com.greplfa.iot.sdk.device.DeviceAlreadyRegisteredException;
import com.greplfa.iot.sdk.device.DeviceInfo;
import com.greplfa.iot.sdk.device.DeviceMethod;
import com.greplfa.web.domain.iot.device.Device;
import com.greplfa.web.domain.iot.device.dto.DevicePreview;
import com.greplfa.web.domain.iot.device.dto.DeviceUpdateForm;
import com.greplfa.web.domain.iot.device.dto.DeviceUpdateFormData;
import com.greplfa.web.domain.iot.device.exception.DeviceNotFoundException;
import com.greplfa.web.domain.iot.device.exception.DeviceUnavailableException;
import com.greplfa.web.domain.iot.device.helper.DeviceMessageSender;
import com.greplfa.web.domain.iot.device.repository.DeviceRepository;
import com.greplfa.web.domain.iot.part.dto.PartUpdateParameter;
import com.greplfa.web.domain.member.Member;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultDeviceService implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceMessageSender message;
    private final DeviceMethod deviceMethod;

    private final static String DEVICE_UUID = "bec6f91b-9adc-40c7-98b5-3374195512ce";

    @Transactional
    @Override
    public void create(String uuid, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);
        check(uuid);
        DeviceInfo deviceInfo = null;
        try {
            deviceInfo = deviceMethod.register(uuid, memberDetails.getId());
        } catch (DeviceAlreadyRegisteredException e) {
            throw new DeviceUnavailableException("장치 등록 실패(이미 등록된 장비)", "이미 등록된 장비 입니다.");
        }
        Device device = Device.create(deviceInfo, member);
        deviceRepository.save(device);
    }

    @Transactional
    @Override
    public void update(
            long id,
            String name,
            List<PartUpdateParameter> partUpdateParameterList,
            MemberDetails memberDetails
    ) {
        Member member = getMember(memberDetails);

        Device device = findDevice(id, member);

        device.update(name, partUpdateParameterList);

        message.update(device, memberDetails);
    }

    @Transactional
    @Override
    public void delete(long id, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        Device device = findDevice(id, member);

        deviceRepository.delete(device);

        message.delete(id, memberDetails);
    }

    @Transactional
    @Override
    public DeviceUpdateForm getUpdateForm(long deviceId, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        DeviceUpdateFormData data = findDeviceFormData(deviceId, member);

        return DeviceUpdateForm.form(data);
    }

    @Transactional
    @Override
    public List<DevicePreview> getInfoList(MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        return deviceRepository.findAllPreviewByOwner(member);
    }

    private DeviceUpdateFormData findDeviceFormData(long deviceId, Member member) {
        return deviceRepository.findUpdateFormDataByIdAndOwner(deviceId, member)
                .orElseThrow(DeviceNotFoundException::new);
    }

    private void check(String deviceUuid) {
        if (deviceRepository.existsByUuid(DEVICE_UUID)) {
            throw new DeviceUnavailableException("장치 검사 실패(이유 : 이미 사용중인 장치)", "이미 사용하고 있는 장치입니다.");
        }
    }

    private Device findDevice(long deviceId, Member member) {
        return deviceRepository.findByIdAndOwner(deviceId, member)
                .orElseThrow(DeviceNotFoundException::new);
    }

    private Member getMember(MemberDetails memberDetails) {
        return Member.from(memberDetails);
    }
}
