package com.greplfa.web.domain.iot.widget.device.service;

import com.greplfa.web.domain.iot.widget.device.dto.DeviceWidgetCreateForm;
import com.greplfa.web.domain.member.dto.MemberDetails;

public interface DeviceWidgetService {
    void create(long deviceId, MemberDetails memberDetails);

    void delete(long widgetId, MemberDetails memberDetails);

    DeviceWidgetCreateForm getCreateForm(MemberDetails memberDetails);

    DeviceWidgetCreateForm getCreateForm(DeviceWidgetCreateForm form, MemberDetails memberDetails);
}
