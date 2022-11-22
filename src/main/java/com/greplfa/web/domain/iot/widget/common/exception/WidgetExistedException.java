package com.greplfa.web.domain.iot.widget.common.exception;

import com.greplfa.web.domain.common.exception.FieldException;
import lombok.Getter;

@Getter
public class WidgetExistedException extends FieldException {
    private static final String REASON = "동일한 위젯이 존재하고 있습니다.";

    public WidgetExistedException(String msg) {
        super(msg, "deviceId" ,REASON);
    }
}
