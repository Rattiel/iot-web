package com.greplfa.web.domain.common.controller;

import com.greplfa.web.domain.iot.device.controller.DeviceController;
import com.greplfa.web.domain.iot.widget.device.controller.DeviceWidgetController;
import com.greplfa.web.domain.iot.widget.history.controller.HistoryWidgetController;
import com.greplfa.web.domain.iot.widget.part.controller.PartWidgetController;
import com.greplfa.web.domain.member.controller.MemberController;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice(
        assignableTypes = {
                BaseController.class,
                MemberController.class,
                DeviceController.class,
                DeviceWidgetController.class,
                PartWidgetController.class,
                HistoryWidgetController.class,
                ExceptionAdviceController.class
        }
)
@Controller
public class ModelAttributeAdviceController {
    @ModelAttribute
    public void bindMember(
            @AuthenticationPrincipal MemberDetails member,
            Model model
    ) {
        if (member != null) {
            model.addAttribute("member", member);
        }
    }

    @GetMapping("/fragment")
    public String fragment() {
        return "fragment";
    }
}
