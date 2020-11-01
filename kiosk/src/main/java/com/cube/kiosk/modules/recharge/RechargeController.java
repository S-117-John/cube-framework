package com.cube.kiosk.modules.recharge;


import com.cube.kiosk.modules.patient.service.PatientService;
import com.cube.kiosk.modules.pay.service.PayService;
import com.cube.kiosk.modules.recharge.service.RechargeService;
import com.cube.kiosk.modules.recharge.service.TransactionRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/recharge")
@Api(value = "充值",tags = "充值")
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PayService payService;




}
