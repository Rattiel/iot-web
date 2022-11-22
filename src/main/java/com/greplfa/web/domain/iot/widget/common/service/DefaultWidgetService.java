package com.greplfa.web.domain.iot.widget.common.service;

import com.greplfa.web.domain.iot.widget.Widget;
import com.greplfa.web.domain.iot.widget.common.repository.WidgetRepository;
import com.greplfa.web.domain.member.Member;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultWidgetService implements WidgetService {
    private final WidgetRepository widgetRepository;

    @Override
    public List<Widget> getList(MemberDetails memberDetails) {
        Member member = getMember(memberDetails);
        return widgetRepository.findAllByOwner(member);
    }

    private Member getMember(MemberDetails memberDetails) {
        return Member.from(memberDetails);
    }
}
