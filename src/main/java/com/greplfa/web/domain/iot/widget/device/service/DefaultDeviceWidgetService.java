package com.greplfa.web.domain.iot.widget.device.service;

import com.greplfa.web.domain.iot.device.Device;
import com.greplfa.web.domain.iot.device.dto.DeviceOption;
import com.greplfa.web.domain.iot.device.exception.DeviceNotFoundException;
import com.greplfa.web.domain.iot.device.repository.DeviceRepository;
import com.greplfa.web.domain.iot.widget.WidgetType;
import com.greplfa.web.domain.iot.widget.common.exception.WidgetExistedException;
import com.greplfa.web.domain.iot.widget.common.exception.WidgetNotFoundException;
import com.greplfa.web.domain.iot.widget.common.helper.WidgetMessageSender;
import com.greplfa.web.domain.iot.widget.device.DeviceWidget;
import com.greplfa.web.domain.iot.widget.device.dto.DeviceWidgetCreateForm;
import com.greplfa.web.domain.iot.widget.device.repository.DeviceWidgetRepository;
import com.greplfa.web.domain.member.Member;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultDeviceWidgetService implements DeviceWidgetService {
    private final DeviceWidgetRepository deviceWidgetRepository;
    private final DeviceRepository deviceRepository;
    private final WidgetMessageSender widgetMessage;

    @Transactional
    @Override
    public void create(long deviceId, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        Device device = findDevice(deviceId, member);

        check(device);

        DeviceWidget widget = DeviceWidget.create(device, member);

        deviceWidgetRepository.save(widget);

        widgetMessage.create(widget, memberDetails);
    }

    @Transactional
    @Override
    public void delete(long widgetId, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        DeviceWidget widget = findWidget(widgetId, member);

        deviceWidgetRepository.delete(widget);

        widgetMessage.delete(widgetId, memberDetails);
    }

    private void check(Device device) {
        if (deviceWidgetRepository.existsByDevice(device)) {
            throw new WidgetExistedException("디바이스 위젯 추가 실패(이유 : 동일한 장비가 존재 중)");
        }
    }

    @Transactional
    @Override
    public DeviceWidgetCreateForm getCreateForm(MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        List<DeviceOption> deviceOptions = findDeviceOption(member);

        return DeviceWidgetCreateForm.from(deviceOptions);
    }

    @Transactional
    @Override
    public DeviceWidgetCreateForm getCreateForm(DeviceWidgetCreateForm form, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        List<DeviceOption> deviceOptions = findDeviceOption(member);

        return form.update(deviceOptions);
    }

    private List<DeviceOption> findDeviceOption(Member member) {
        List<Device> existedDeviceList = deviceWidgetRepository.findAllByOwnerAndType(member, WidgetType.DEVICE);


        if (existedDeviceList.size() == 0) {
            return deviceRepository.findAllOptionByOwner(member);
        } else {
            return deviceRepository.findAllOptionByOwnerAndDeviceNotIn(member, existedDeviceList);
        }
    }

    private DeviceWidget findWidget(long widgetId, Member member) {
        return deviceWidgetRepository.findByIdAndOwner(widgetId, member)
                .orElseThrow(WidgetNotFoundException::new);
    }

    private Device findDevice(long deviceId, Member member) {
        return deviceRepository.findByIdAndOwner(deviceId, member)
                .orElseThrow(() -> new DeviceNotFoundException("id"));
    }

    private Member getMember(MemberDetails memberDetails) {
        return Member.from(memberDetails);
    }
}
