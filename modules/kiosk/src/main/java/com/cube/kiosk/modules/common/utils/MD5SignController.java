package com.cube.kiosk.modules.common.utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.provider.MD5;

@RestController
@RequestMapping("api/md5")
@Api(value = "md5签名",tags = "md5签名")
public class MD5SignController {

    @GetMapping("")
    @ApiOperation(httpMethod = "GET",value = "获取签名")
    public String sign(String param){
        return MD5Sign.md5(param);
    }
}
