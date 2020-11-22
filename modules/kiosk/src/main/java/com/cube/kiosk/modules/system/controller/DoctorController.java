package com.cube.kiosk.modules.system.controller;

import com.cube.kiosk.modules.system.entity.DeptDO;
import com.cube.kiosk.modules.system.entity.DoctorDO;
import com.cube.kiosk.modules.system.model.DoctorVO;
import com.cube.kiosk.modules.system.repository.DeptRepository;
import com.cube.kiosk.modules.system.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin/doctor")
public class DoctorController {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("")
    public String index(Model model){
        List<DoctorVO> voList = new ArrayList<>();
        List<DoctorDO> doctorList = doctorRepository.findAll();
        doctorList.forEach(doctor -> {
            DoctorVO doctorVO = new DoctorVO();
            doctorVO.setDoctor(doctor);
            DeptDO dept = deptRepository.getOne(doctor.getDeptId());
            doctorVO.setDept(dept);
            voList.add(doctorVO);
        });

        model.addAttribute("list",voList);
        return "modules/doctorList";
    }

    @GetMapping("form")
    public String form(Model model){
        List<DeptDO> deptList = deptRepository.findAll();
        model.addAttribute("deptList",deptList);
        return "modules/doctorForm";
    }

    @RequestMapping("save")
    @ResponseBody
    public String save(DoctorDO doctor){
        doctorRepository.save(doctor);
        return "SUCCESS";
    }

    @RequestMapping("delete")
    public String delete(String id){
        doctorRepository.deleteById(id);
        return "redirect:/admin/doctor";
    }
}
