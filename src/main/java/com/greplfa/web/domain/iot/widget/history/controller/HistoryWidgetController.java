package com.greplfa.web.domain.iot.widget.history.controller;

import com.greplfa.web.domain.common.exception.Fieldable;
import com.greplfa.web.domain.iot.device.exception.DeviceNotFoundException;
import com.greplfa.web.domain.iot.device.exception.DeviceUnavailableException;
import com.greplfa.web.domain.iot.widget.common.exception.WidgetExistedException;
import com.greplfa.web.domain.iot.widget.common.exception.WidgetNotFoundException;
import com.greplfa.web.domain.iot.widget.history.dto.HistoryWidgetCreateForm;
import com.greplfa.web.domain.iot.widget.history.service.HistoryWidgetService;
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
@RequestMapping("/iot/widget/history")
@Controller
public class HistoryWidgetController {
    private final HistoryWidgetService historyWidgetService;

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

        return "/iot/widget/history/create";
    }

    @PostMapping("/new/create")
    public String requestCreate(
            @Valid @ModelAttribute("form") HistoryWidgetCreateForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        if (bindingResult.hasErrors()) {
            bindCreateForm(form, memberDetails);

            return "/iot/widget/history/create";
        }

        try {
            historyWidgetService.create(form.getPartId(), memberDetails);
        } catch (DeviceNotFoundException | DeviceUnavailableException | WidgetExistedException e) {
            bindCreateForm(form, memberDetails);
            bindFieldError(bindingResult, e);
            return "/iot/widget/history/create";
        }

        return "redirect:/";
    }

    @PostMapping("/{widgetId}/delete")
    public String requestDelete(
            @PathVariable Long widgetId,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        historyWidgetService.delete(widgetId, memberDetails);
        return "redirect:/";
    }

    private void bindCreateForm(MemberDetails memberDetails, Model model) {
        HistoryWidgetCreateForm form = historyWidgetService.getCreateForm(memberDetails);
        model.addAttribute("form", form);
    }

    private void bindCreateForm(HistoryWidgetCreateForm form, MemberDetails memberDetails) {
        historyWidgetService.getCreateForm(form, memberDetails);
    }

    private void bindFieldError(BindingResult bindingResult, Fieldable e) {
        FieldError fieldError = new FieldError("form", e.getField(), e.getReason());
        bindingResult.addError(fieldError);
    }
}
