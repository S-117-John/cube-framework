package com.cube.kiosk.modules.system.controller;

import com.cube.kiosk.modules.hardware.utils.StringUtil;
import com.cube.kiosk.modules.security.model.HardWareConfigDO;
import com.cube.kiosk.modules.security.repository.HardWareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@Controller
@ApiIgnore
@RequestMapping("admin/kiosk")
public class KioskController {

    @Autowired
    private HardWareRepository hardWareRepository;

    @RequestMapping("config")
    public String config(Model model){

        List<HardWareConfigDO> hardWareConfigDOList = hardWareRepository.findAll();
        model.addAttribute("configList",hardWareConfigDOList);
        model.addAttribute("page","modules/kioskConfig");
        model.addAttribute("fragment","kiosk_config");
        return "index";
    }

    @RequestMapping("config/form")
    public String form(Model model){

        model.addAttribute("page","modules/kioskConfigForm");
        model.addAttribute("fragment","kiosk_config_form");
        return "index";
    }


    @RequestMapping("config/edit")
    public String edit(String id, Model model){
        Optional<HardWareConfigDO> optional = hardWareRepository.findById(id);
        if(optional.isPresent()){
            HardWareConfigDO hardWareConfigDO = optional.get();
            model.addAttribute("config",hardWareConfigDO);
        }
        model.addAttribute("page","modules/kioskConfigEdit");
        model.addAttribute("fragment","kiosk_config_edit");
        return "index";
    }

    @RequestMapping("config/save")
    public String save(HardWareConfigDO hardWareConfigDO, Model model){
        hardWareRepository.save(hardWareConfigDO);
        return "redirect:/admin/kiosk/config";
    }

}
