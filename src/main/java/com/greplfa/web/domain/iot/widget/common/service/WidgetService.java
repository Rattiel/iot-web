package com.greplfa.web.domain.iot.widget.common.service;

import com.greplfa.web.domain.iot.widget.Widget;
import com.greplfa.web.domain.member.dto.MemberDetails;

import java.util.List;

public interface WidgetService {
    List<Widget> getList(MemberDetails memberDetails);
}
