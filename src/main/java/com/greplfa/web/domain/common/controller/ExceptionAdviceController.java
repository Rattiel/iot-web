package com.greplfa.web.domain.common.controller;

import com.greplfa.web.domain.iot.device.controller.DeviceController;
import com.greplfa.web.domain.iot.widget.device.controller.DeviceWidgetController;
import com.greplfa.web.domain.iot.widget.history.controller.HistoryWidgetController;
import com.greplfa.web.domain.iot.widget.part.controller.PartWidgetController;
import com.greplfa.web.domain.member.controller.MemberController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(
        assignableTypes = {
                BaseController.class,
                MemberController.class,
                DeviceController.class,
                PartWidgetController.class,
                HistoryWidgetController.class,
                DeviceWidgetController.class
        }
)
@Controller
public class ExceptionAdviceController {
    @ExceptionHandler({
            RuntimeException.class,
            Exception.class
    })
    public String internalServerError(Exception e) {
        log.error(e.getMessage());

        return "error/500";
    }

    @ExceptionHandler({
            DataAccessException.class
    })
    public String serviceUnavailable(Exception e) {
        log.error(e.getMessage());

        return "error/503";
    }
}