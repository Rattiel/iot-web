package com.greplfa.web.domain.iot.widget.common.controller;

import com.greplfa.web.domain.iot.widget.Widget;
import com.greplfa.web.domain.iot.widget.common.service.WidgetService;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/iot/widget")
@RestController
public class WidgetController {
    private final WidgetService widgetService;

    @PostMapping
    public List<Widget> renderListPage(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        return widgetService.getList(memberDetails);
    }
}
