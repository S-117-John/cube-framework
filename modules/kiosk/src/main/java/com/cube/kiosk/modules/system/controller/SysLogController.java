package com.cube.kiosk.modules.system.controller;

import com.cube.core.system.entity.SystemLogDO;
import com.cube.core.system.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/sys/log")
public class SysLogController {

    @Autowired
    private SystemLogRepository systemLogRepository;

    @RequestMapping("")
    public String list(Model model){

        List<SystemLogDO> systemLogList = systemLogRepository.findAll(Sort.by("createDate").descending());
        model.addAttribute("sysLogList",systemLogList);
        model.addAttribute("page","modules/sysLog");
        model.addAttribute("fragment","sys_log");
        return "index";
    }
}
