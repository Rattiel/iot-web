package com.greplfa.web.domain.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    @GetMapping(value = {"/", "/index", "/iot/widget"})
    public String index() {
        return "index";
    }
}
