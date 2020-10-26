package com.cube.kiosk.modules.hospitalRecharge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hospital/register")
public class HospitalRechargeController {

    @GetMapping("")
    public String index(){

        return "";
    }

    @GetMapping(value = "{cardId}/{payType}/{money}")
    public String getQrCode(@PathVariable String cardId,
                            @PathVariable String payType,
                            @PathVariable String money){
        System.out.println(cardId);
        return "";
    }

    @GetMapping("{cardId}/{orderId}")
    public String getPayResult(@PathVariable String cardId,
                               @PathVariable String orderId){
        return "";
    }
}
