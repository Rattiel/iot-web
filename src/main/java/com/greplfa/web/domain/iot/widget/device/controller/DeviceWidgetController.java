package com.greplfa.web.domain.iot.widget.device.controller;

import com.greplfa.web.domain.common.exception.Fieldable;
import com.greplfa.web.domain.iot.device.exception.DeviceNotFoundException;
import com.greplfa.web.domain.iot.device.exception.DeviceUnavailableException;
import com.greplfa.web.domain.iot.widget.common.exception.WidgetExistedException;
import com.greplfa.web.domain.iot.widget.common.exception.WidgetNotFoundException;
import com.greplfa.web.domain.iot.widget.device.dto.DeviceWidgetCreateForm;
import com.greplfa.web.domain.iot.widget.device.service.DeviceWidgetService;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/iot/widget/device")
@Controller
public class DeviceWidgetController {
    private final DeviceWidgetService deviceWidgetService;

    @ExceptionHandler({
            WidgetNotFoundException.class
    })
    public String redirectIndex() {
        return "redirect:/";
    }

    @GetMapping("/new/create")
    public String renderCreatePage(
            Model model,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        bindCreateForm(memberDetails, model);

        return "/iot/widget/device/create";
    }

    @PostMapping("/new/create")
    public String requestCreate(
            @Valid @ModelAttribute("form") DeviceWidgetCreateForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        if (bindingResult.hasErrors()) {
            bindCreateForm(form, memberDetails);
            return "/iot/widget/device/create";
        }

        try {
            deviceWidgetService.create(form.getDeviceId(), memberDetails);
        } catch (DeviceNotFoundException | DeviceUnavailableException | WidgetExistedException e) {
            bindCreateForm(form, memberDetails);
            bindFieldError(bindingResult, e);
            return "/iot/widget/device/create";
        }

        return "redirect:/";
    }

    @PostMapping("/{widgetId}/delete")
    public String requestDelete(
            @PathVariable Long widgetId,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        deviceWidgetService.delete(widgetId, memberDetails);
        return "redirect:/";
    }

    private void bindCreateForm(MemberDetails memberDetails, Model model) {
        DeviceWidgetCreateForm form = deviceWidgetService.getCreateForm(memberDetails);
        model.addAttribute("form", form);
    }

    private void bindCreateForm(DeviceWidgetCreateForm form, MemberDetails memberDetails) {
        deviceWidgetService.getCreateForm(form, memberDetails);
    }

    private void bindFieldError(BindingResult bindingResult, Fieldable e) {
        FieldError fieldError = new FieldError("form", e.getField(), e.getReason());
        bindingResult.addError(fieldError);
    }
}
