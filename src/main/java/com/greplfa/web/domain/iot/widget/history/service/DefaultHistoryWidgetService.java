package com.greplfa.web.domain.iot.widget.history.service;

import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.iot.part.dto.PartOption;
import com.greplfa.web.domain.iot.part.exception.PartNotFoundException;
import com.greplfa.web.domain.iot.part.repository.PartRepository;
import com.greplfa.web.domain.iot.widget.WidgetType;
import com.greplfa.web.domain.iot.widget.common.exception.WidgetExistedException;
import com.greplfa.web.domain.iot.widget.common.exception.WidgetNotFoundException;
import com.greplfa.web.domain.iot.widget.common.helper.WidgetMessageSender;
import com.greplfa.web.domain.iot.widget.history.HistoryWidget;
import com.greplfa.web.domain.iot.widget.history.dto.HistoryWidgetCreateForm;
import com.greplfa.web.domain.iot.widget.history.repository.HistoryWidgetRepository;
import com.greplfa.web.domain.member.Member;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultHistoryWidgetService implements HistoryWidgetService {
    private final HistoryWidgetRepository historyWidgetRepository;

    private final PartRepository partRepository;

    private final WidgetMessageSender widgetMessage;

    @Transactional
    @Override
    public void create(long partId, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        Part part = findPart(partId, member);

        check(part);

        HistoryWidget widget = HistoryWidget.create(part.getDevice().getId(), part, member);

        historyWidgetRepository.save(widget);

        widgetMessage.create(widget, memberDetails);
    }

    @Transactional
    @Override
    public void delete(long widgetId, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        HistoryWidget widget = findWidget(widgetId, member);

        historyWidgetRepository.delete(widget);

        widgetMessage.delete(widgetId, memberDetails);
    }

    @Transactional
    @Override
    public HistoryWidgetCreateForm getCreateForm(MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        List<PartOption> partOptionList = findPartOption(member);

        return HistoryWidgetCreateForm.from(partOptionList);
    }

    @Transactional
    @Override
    public HistoryWidgetCreateForm getCreateForm(HistoryWidgetCreateForm form, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        List<PartOption> partOptionList = findPartOption(member);

        return form.update(partOptionList);
    }

    private List<PartOption> findPartOption(Member member) {
        List<Part> existedPartList = historyWidgetRepository.findAllByOwnerAndType(member, WidgetType.HISTORY);

        if (existedPartList.size() == 0) {
            return partRepository.findAllOptionByOwner(member);
        } else {
            return partRepository.findAllOptionByOwnerAndPartNotIn(member, existedPartList);
        }
    }

    private void check(Part part) {
        if (historyWidgetRepository.existsByPart(part)) {
            throw new WidgetExistedException("기록 위젯 추가 실패(이유 : 동일한 파츠가 존재 중)");
        }
    }

    private Part findPart(long partId, Member member) {
        return partRepository.findByIdAndOwner(partId, member)
                .orElseThrow(PartNotFoundException::new);
    }

    private HistoryWidget findWidget(long widgetId, Member member) {
        return historyWidgetRepository.findByIdAndOwner(widgetId, member)
                .orElseThrow(WidgetNotFoundException::new);
    }

    private Member getMember(MemberDetails memberDetails) {
        return Member.from(memberDetails);
    }
}
