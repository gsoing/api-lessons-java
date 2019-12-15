package com.tplock.locking.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerEndpoint {
    @RequestMapping("/")
    public String home() {
        return "redirect:/swagger-ui.html";
    }
}
