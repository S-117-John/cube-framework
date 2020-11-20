package com.cube.kiosk.modules.system.controller;

import com.cube.kiosk.modules.system.entity.DeptDO;
import com.cube.kiosk.modules.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("form")
    public String form(){
        return "modules/deptForm";
    }

    @RequestMapping("")
    public String index(Model model){
        List<DeptDO> deptList = deptService.list();
        model.addAttribute("list",deptList);
        return "modules/deptList";
    }

    @RequestMapping("save")
    @ResponseBody
    public String save(DeptDO deptDO){
        deptService.save(deptDO);
        return "success";
    }

    @RequestMapping("delete")
    public String delete(String id){
        deptService.delete(id);
        return "redirect:/admin/dept";
    }
}
