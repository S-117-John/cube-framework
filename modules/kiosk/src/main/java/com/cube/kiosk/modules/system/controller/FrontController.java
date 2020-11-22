package com.cube.kiosk.modules.system.controller;

import com.cube.kiosk.modules.system.entity.DeptDO;
import com.cube.kiosk.modules.system.entity.DoctorDO;
import com.cube.kiosk.modules.system.entity.HospitalDO;
import com.cube.kiosk.modules.system.repository.DeptRepository;
import com.cube.kiosk.modules.system.repository.DoctorRepository;
import com.cube.kiosk.modules.system.repository.HospitalRepository;
import com.cube.kiosk.modules.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("front")
public class FrontController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("")
    public String index(Model model){
        HospitalDO hospitalDO = hospitalRepository.getOne("1");
        model.addAttribute("content",hospitalDO);
        return "front/index";
    }

    @GetMapping("dept")
    public String dept(Model model){

        List<DeptDO> deptList = deptService.list();
        model.addAttribute("list",deptList);
        return "front/dept";
    }

    @RequestMapping("dept/detail")
    public String deptDetail(String id,Model model){
        DeptDO dept = deptService.getOne(id);
        model.addAttribute("dept",dept);
        return "front/deptDetail";
    }

    @GetMapping("doctor")
    public String doctor(Model model){
        List<DeptDO> deptList = deptService.list();
        model.addAttribute("deptList",deptList);
        return "front/doctor";
    }

    @GetMapping("doctor/list")
    public String doctorList(String id,Model model){
        List<DoctorDO> doctorList = doctorRepository.findByDeptId(id);
        model.addAttribute("doctorList",doctorList);
        return "front/doctorList";
    }

    @GetMapping("doctor/detail")
    public String doctorDetail(String id,Model model){
        DoctorDO doctor = doctorRepository.getOne(id);
        model.addAttribute("doctor",doctor);
        return "front/doctorDetail";
    }

}
