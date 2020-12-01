package com.cube.kiosk.modules.system.controller;


import com.cube.kiosk.modules.log.entity.TransLogDO;
import com.cube.kiosk.modules.pay.model.TransactionData;
import com.cube.kiosk.modules.pay.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore
@Controller
@RequestMapping("admin/trans/record")
public class TransController {

    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping("")
    public String list(Model model){

        List<TransactionData> systemLogList = transactionRepository.findAll(Sort.by("creatDate").descending());
        model.addAttribute("transList",systemLogList);
        model.addAttribute("page","modules/transRecord");
        model.addAttribute("fragment","trans_record");
        return "index";
    }
}
