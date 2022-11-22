package com.greplfa.web.domain.iot.widget.common.helper;

import com.greplfa.web.domain.iot.widget.Widget;
import com.greplfa.web.domain.member.dto.MemberDetails;

public interface WidgetMessageSender {
    void create(Widget widget, MemberDetails memberDetails);

    void update(Widget widget, MemberDetails memberDetails);

    void delete(long widgetId, MemberDetails memberDetails);
}
