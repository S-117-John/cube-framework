package com.cube.kiosk.modules.recharge;

import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.model.PatientInfo;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.common.utils.NetUtil;
import com.cube.kiosk.modules.hardware.AllowCardIn;
import com.cube.kiosk.modules.hardware.CheckCard;
import com.cube.kiosk.modules.hardware.MoveCard;
import com.cube.kiosk.modules.hardware.ReadCard;
import com.cube.kiosk.modules.patient.service.PatientService;
import com.cube.kiosk.modules.pay.model.RequestDataPay;
import com.cube.kiosk.modules.pay.model.ResponseDataPay;
import com.cube.kiosk.modules.pay.service.PayService;
import com.cube.kiosk.modules.recharge.service.RechargeService;
import com.cube.kiosk.modules.recharge.service.TransactionRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @AllowCardIn
    @CheckCard
    @MoveCard
    @ReadCard
    @RequestMapping("")
    @SysLog("门诊预存")
    public ResponseData<PatientInfo> index(String cardId, HttpServletRequest request){

        final Object[] objects = new Object[1];

        patientService.get(cardId, new ResultListener() {
            @Override
            public void success(Object object) {
                objects[0] = object;
            }

            @Override
            public void error(Object object) {
                objects[0] = object;
            }

            @Override
            public void exception(Object object) {
                objects[0] = object;
            }
        });
       return (ResponseData<PatientInfo>) objects[0];
    }


    /**
     *主扫下单
     * @param money
     * @param payType 支付方式
     * @return
     */
    @GetMapping("qrCode")
    @SysLog("门诊预存-获取支付二维码")
    public ResponseData<ResponseDataPay> getQrCode(int money, String payType, HttpServletRequest request){
        final Object[] objects = new Object[1];

        RequestDataPay requestDataPay = new RequestDataPay();
        requestDataPay.setRequestIp(NetUtil.getIPAddress(request));
        requestDataPay.setTxnAmt(money*10*10+"");
        payService.doPost(requestDataPay, new ResultListener() {
            @Override
            public void success(Object object) {
                objects[0] = object;
            }

            @Override
            public void error(Object object) {
                objects[0] = object;
            }

            @Override
            public void exception(Object object) {
                objects[0] = object;
            }
        });

        return (ResponseData<ResponseDataPay>) objects[0];
    }

    /**
     *
     * @param tradeNo
     * @param request
     * @return
     */
    @GetMapping("queryResult")
    @SysLog("门诊预存-查询支付结果")
    public ResponseData<ResponseDataPay> queryResult(String tradeNo, HttpServletRequest request){
        RequestDataPay requestDataPay = new RequestDataPay();
        final Object[] objects = new Object[1];
        payService.queryResult(requestDataPay, new ResultListener() {
            @Override
            public void success(Object object) {
                objects[0] = object;
            }

            @Override
            public void error(Object object) {
                objects[0] = object;
            }

            @Override
            public void exception(Object object) {
                objects[0] = object;
            }
        });
        return (ResponseData<ResponseDataPay>) objects[0];
    }


}
