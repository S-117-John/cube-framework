package com.cube.kiosk.modules;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping("admin/index")
public class IndexController {

    @GetMapping
    public String index(Model model){
        model.addAttribute("page","modules/dashboard");
        model.addAttribute("fragment","dashboard");
        return "index";
    }
}
