package com.cube.kiosk.modules.mock;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {

    @PostMapping("comlink-interface-abc-forward/comlink/pay")
    public String pay(String requestJson){
        System.out.println(requestJson);
        return "ok";
    }
}
