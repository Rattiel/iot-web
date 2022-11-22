package com.greplfa.web.domain.iot.widget.history.service;

import com.greplfa.web.domain.iot.widget.history.dto.HistoryWidgetCreateForm;
import com.greplfa.web.domain.member.dto.MemberDetails;

public interface HistoryWidgetService {
    void create(long partId, MemberDetails memberDetails);

    void delete(long widgetId, MemberDetails memberDetails);

    HistoryWidgetCreateForm getCreateForm(MemberDetails memberDetails);

    HistoryWidgetCreateForm getCreateForm(HistoryWidgetCreateForm form, MemberDetails memberDetails);
}
