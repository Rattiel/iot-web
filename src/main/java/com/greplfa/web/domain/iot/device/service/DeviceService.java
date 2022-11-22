package com.greplfa.web.domain.iot.device.service;

import com.greplfa.web.domain.iot.device.dto.DevicePreview;
import com.greplfa.web.domain.iot.device.dto.DeviceUpdateForm;
import com.greplfa.web.domain.iot.part.dto.PartUpdateParameter;
import com.greplfa.web.domain.member.dto.MemberDetails;

import java.util.List;

public interface DeviceService {
    void create(String uuid, MemberDetails memberDetails);

    void update(long id, String name, List<PartUpdateParameter> partUpdateParameterList, MemberDetails memberDetails);

    void delete(long id, MemberDetails memberDetails);

    DeviceUpdateForm getUpdateForm(long deviceId, MemberDetails memberDetails);

    List<DevicePreview> getInfoList(MemberDetails memberDetails);
}
