package com.cube.kiosk.modules.system.controller;

import com.cube.kiosk.modules.system.entity.HospitalDO;
import com.cube.kiosk.modules.system.repository.DeptRepository;
import com.cube.kiosk.modules.system.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping("admin/hospital")
public class HospitalController {

    @Autowired
    private HospitalRepository hospitalRepository;



    @RequestMapping("save")
    public String save(HospitalDO hospitalDO){
        hospitalDO.setId("1");
        hospitalRepository.save(hospitalDO);
        return "redirect:/admin/index";
    }

    @GetMapping("")
    public String index(){
        return "modules/hospitalForm";
    }
}
