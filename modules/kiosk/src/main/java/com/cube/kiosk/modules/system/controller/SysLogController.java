package com.cube.kiosk.modules.system.controller;

import com.cube.core.system.entity.SystemLogDO;
import com.cube.core.system.repository.SystemLogRepository;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/sys/log")
public class SysLogController {

    @Autowired
    private SystemLogRepository systemLogRepository;

    private static Map<String,Object> operMap = new HashMap<>();
    static {
        operMap.put("com.cube.kiosk.modules.register.controller.RegisterController.register","门诊办卡");
        operMap.put("com.cube.kiosk.modules.patient.controller.PatientController.delete","撤销办卡");
        operMap.put("com.cube.kiosk.modules.pay.controller.PayController.cashSave","现金充值");
    }

    @RequestMapping("")
    public String list(Model model){

        List<SystemLogDO> systemLogList = systemLogRepository.findAll(Sort.by("createDate").descending());
        systemLogList.forEach(systemLogDO -> {
            systemLogDO.setOperation(MapUtils.getString(operMap,systemLogDO.getMethod(),""));
        });
        model.addAttribute("sysLogList",systemLogList);
        model.addAttribute("page","modules/sysLog");
        model.addAttribute("fragment","sys_log");
        return "index";
    }
}
