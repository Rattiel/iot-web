package com.greplfa.web.domain.iot.device.controller;

import com.greplfa.web.domain.common.exception.Fieldable;
import com.greplfa.web.domain.iot.device.dto.DeviceCreateForm;
import com.greplfa.web.domain.iot.device.dto.DevicePreview;
import com.greplfa.web.domain.iot.device.dto.DeviceUpdateForm;
import com.greplfa.web.domain.iot.device.exception.DeviceNotFoundException;
import com.greplfa.web.domain.iot.device.exception.DeviceUnavailableException;
import com.greplfa.web.domain.iot.device.service.DeviceService;
import com.greplfa.web.domain.iot.part.dto.PartUpdateParameter;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/iot/device")
@Controller
public class DeviceController {
    private final DeviceService deviceService;

    @ExceptionHandler({DeviceNotFoundException.class})
    public String redirectIndex() {
        return "redirect:/";
    }

    @GetMapping
    public String renderSettingsPage(
            Model model,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        bindDeviceInfoList(model, memberDetails);

        return "iot/device/list";
    }

    @GetMapping("/new/create")
    public String renderCreatePage(
            Model model
    ) {
        bindCreateForm(model);

        return "iot/device/create";
    }

    @PostMapping("/new/create")
    public String requestRegister(
            @Valid @ModelAttribute("form") DeviceCreateForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        if (bindingResult.hasErrors()) {
            return "iot/device/create";
        }

        try {
            deviceService.create(form.getUuid(), memberDetails);
        } catch (DeviceUnavailableException e) {
            bindFieldError(bindingResult, e);
            return "iot/device/create";
        }

        return "redirect:/iot/device";
    }

    @GetMapping("/{deviceId}/update")
    public String renderUpdatePage(
            @PathVariable Long deviceId,
            Model model,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        bindUpdateForm(deviceId, memberDetails, model);

        return "iot/device/update";
    }

    @PostMapping("/{deviceId}/update")
    public String requestUpdate(
            @PathVariable Long deviceId,
            @Valid @ModelAttribute("form") DeviceUpdateForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        if (bindingResult.hasErrors()) {
            return "iot/device/update";
        }

        try {
            List<PartUpdateParameter> partUpdateParameterList = form.getParts()
                    .stream()
                    .map(PartUpdateParameter::from)
                    .toList();

            deviceService.update(deviceId, form.getName(), partUpdateParameterList, memberDetails);
        } catch (DeviceUnavailableException e) {
            bindFieldError(bindingResult, e);
            return "iot/device/update";
        }

        return "redirect:/iot/device";
    }

    @PostMapping("/{deviceId}/delete")
    public String requestDelete(
            @PathVariable Long deviceId,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        deviceService.delete(deviceId, memberDetails);

        return "redirect:/iot/device";
    }

    private void bindDeviceInfoList(Model model, MemberDetails memberDetails) {
        List<DevicePreview> deviceList = deviceService.getInfoList(memberDetails);
        model.addAttribute("deviceList", deviceList);
    }

    private void bindCreateForm(Model model) {
        DeviceCreateForm form = DeviceCreateForm.of();
        model.addAttribute("form", form);
    }

    private void bindUpdateForm(long deviceId, MemberDetails memberDetails, Model model) {
        DeviceUpdateForm form = deviceService.getUpdateForm(deviceId, memberDetails);
        model.addAttribute("form", form);
    }

    private void bindFieldError(BindingResult bindingResult, Fieldable e) {
        FieldError fieldError = new FieldError("form", e.getField(), e.getReason());
        bindingResult.addError(fieldError);
    }
}
