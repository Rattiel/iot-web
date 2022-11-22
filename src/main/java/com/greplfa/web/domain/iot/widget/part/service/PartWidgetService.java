package com.greplfa.web.domain.iot.widget.part.service;

import com.greplfa.web.domain.iot.widget.part.dto.PartWidgetCreateForm;
import com.greplfa.web.domain.member.dto.MemberDetails;

public interface PartWidgetService {
    void create(long partId, MemberDetails memberDetails);

    void delete(long widgetId, MemberDetails memberDetails);

    PartWidgetCreateForm getCreateForm(MemberDetails memberDetails);

    PartWidgetCreateForm getCreateForm(PartWidgetCreateForm form, MemberDetails memberDetails);
}
