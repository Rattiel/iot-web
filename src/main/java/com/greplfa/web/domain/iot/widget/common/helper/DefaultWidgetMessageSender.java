package com.greplfa.web.domain.iot.widget.common.helper;

import com.greplfa.web.domain.iot.widget.Widget;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultWidgetMessageSender implements WidgetMessageSender {
    private final static String MESSAGE_PREFIX = "/iot/widget/";
    private final SimpMessagingTemplate websocket;

    @Override
    public void create(Widget widget, MemberDetails memberDetails) {
        this.websocket.convertAndSendToUser(memberDetails.getUsername(), MESSAGE_PREFIX + "create", widget);
    }

    @Override
    public void update(Widget widget, MemberDetails memberDetails) {
        this.websocket.convertAndSendToUser(memberDetails.getUsername(), MESSAGE_PREFIX + "update", widget);
    }

    @Override
    public void delete(long widgetId, MemberDetails memberDetails) {
        this.websocket.convertAndSendToUser(
                memberDetails.getUsername(),
                MESSAGE_PREFIX + "delete", "{\"id\" : " + widgetId + "}"
        );
    }
}
