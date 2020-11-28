package com.cube.kiosk.modules.system.controller;

import com.cube.core.system.entity.SystemLogDO;
import com.cube.kiosk.modules.log.entity.HisHttpsLog;
import com.cube.kiosk.modules.log.repository.HisHttpsLogRepository;
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
@RequestMapping("admin/his/log")
public class HisLogController {

    @Autowired
    private HisHttpsLogRepository hisHttpsLogRepository;

    private static Map<String,Object> map = new HashMap<>();

    static {
        map.put("his/revokeCard","撤销办卡");
        map.put("his/cardIssuers","门诊办卡");
    }

    @RequestMapping("")
    public String list(Model model){

        List<HisHttpsLog> systemLogList = hisHttpsLogRepository.findAll(Sort.by("createTime").descending());
        systemLogList.forEach(hisHttpsLog -> {
            hisHttpsLog.setOper(MapUtils.getString(map,hisHttpsLog.getNote(),""));
        });
        model.addAttribute("hisLogList",systemLogList);
        model.addAttribute("page","modules/hisLog");
        model.addAttribute("fragment","his_log");
        return "index";
    }
}
