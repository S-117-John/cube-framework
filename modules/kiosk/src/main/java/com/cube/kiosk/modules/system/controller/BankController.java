package com.cube.kiosk.modules.system.controller;

import com.cube.kiosk.modules.log.entity.HisHttpsLog;
import com.cube.kiosk.modules.log.entity.TransLogDO;
import com.cube.kiosk.modules.log.repository.TransLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/bank/log")
public class BankController {

    @Autowired
    private TransLogRepository transLogRepository;

    @RequestMapping("")
    public String list(Model model){

        List<TransLogDO> systemLogList = transLogRepository.findAll(Sort.by("createTime").descending());
        model.addAttribute("bankLogList",systemLogList);
        model.addAttribute("page","modules/bankLog");
        model.addAttribute("fragment","bank_log");
        return "index";
    }
}
