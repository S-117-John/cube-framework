package com.cube.kiosk.modules.security;

import com.cube.kiosk.modules.security.model.HardWareDO;
import com.cube.kiosk.modules.security.model.HardWareParam;
import com.cube.kiosk.modules.security.repository.HardWareRepository;
import com.cube.kiosk.modules.security.service.HardWareRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/hardware")
@Api(value = "自助机注册",tags = "自助机注册")
public class HardWareRegisterController {

    @Autowired
    private HardWareRepository hardWareRepository;

    @Autowired
    private HardWareRegisterService hardWareRegisterService;

    @PostMapping("")
    @ApiOperation(httpMethod = "POST",value = "自助机注册")
    public String index(HardWareParam param){
        HardWareDO hardWareDO = new HardWareDO();
        hardWareDO.setIp(param.getIp());
        hardWareDO.setName(param.getName());
        hardWareDO.setNote(param.getNote());
        hardWareRegisterService.save(hardWareDO);
        hardWareRegisterService.getAllHardWare();
        return "保存成功";
    }

    @GetMapping("")
    @ApiOperation(httpMethod = "GET",value = "测试自助机注册缓存")
    public List<HardWareDO> test(){
        return hardWareRegisterService.getAllHardWare();
    }

}
