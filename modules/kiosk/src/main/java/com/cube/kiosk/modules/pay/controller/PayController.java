package com.cube.kiosk.modules.pay.controller;

import com.cube.common.utils.IpUtil;
import com.cube.common.utils.SnowflakeIdWorker;
import com.cube.core.global.anno.ResponseApi;
import com.cube.core.system.annotation.SysLog;
import com.cube.kiosk.modules.anno.Access;
import com.cube.kiosk.modules.common.ResponseData;
import com.cube.kiosk.modules.common.ResponseHisData;
import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.common.utils.HisMd5Sign;
import com.cube.kiosk.modules.common.utils.RestTemplate;
import com.cube.kiosk.modules.patient.model.HosPatientDO;
import com.cube.kiosk.modules.patient.model.Patient;
import com.cube.kiosk.modules.patient.repository.HosPatientRepository;
import com.cube.kiosk.modules.patient.repository.PatientRepository;
import com.cube.kiosk.modules.pay.anno.PayParamResolver;
import com.cube.kiosk.modules.pay.model.*;
import com.cube.kiosk.modules.pay.repository.NoticeRepository;
import com.cube.kiosk.modules.pay.repository.TransactionRepository;
import com.cube.kiosk.modules.pay.service.PayService;
import com.cube.kiosk.modules.register.anno.RegisterResolver;
import com.cube.kiosk.modules.register.model.RegisterParam;
import com.cube.kiosk.modules.security.model.HardWareConfigDO;
import com.cube.kiosk.modules.security.repository.HardWareRepository;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/pay")
@Api(value = "支付",tags = "支付")
public class PayController {

    @Value("${neofaith.token}")
    private String token;

    @Value("${neofaith.hosId}")
    private String hosId;


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private PayService payService;

    @Autowired
    private NoticeRepository noticeRepository;

    @ApiOperation(httpMethod = "POST",value = "获取支付二维码")
    @RequestMapping("")
    @SysLog("获取支付二维码")
    public ResponseData<TransactionData> index(@RequestBody PayParam payParam){
        final Object[] objects = new Object[1];
        payService.getQrCode(payParam, new ResultListener() {
            @Override
            public void success(Object object) {
                ResponseData<TransactionData> responseData = new ResponseData<>();
                responseData.setCode("200");
                responseData.setMessage("SUCCESS");
                responseData.setData((TransactionData) object);
                objects[0] = responseData;
            }

            @Override
            public void error(Object object) {
                ResponseData<TransactionData> responseData = new ResponseData<>();
                responseData.setCode("500");
                responseData.setMessage("ERROR");
                responseData.setData((TransactionData) object);
                objects[0] = responseData;
            }

            @Override
            public void exception(Object object) {
                ResponseData<String> responseData = new ResponseData<>();
                responseData.setCode("500");
                responseData.setMessage((String) object);
                responseData.setData(null);
                objects[0] = responseData;
            }
        });
        return (ResponseData<TransactionData>) objects[0];
    }

    @Autowired
    private HardWareRepository hardWareRepository;

    @Value("${app-pay.mid}")
    private String mid;

    @Value("${app-pay.noticeUrl}")
    private String callBack;

    @Autowired
    private TransactionRepository transactionRepository;

    @ApiOperation(httpMethod = "POST",value = "获取支付二维码")
    @RequestMapping("index")
    public Object getQrCode(@RequestBody PayParam payParam){
        HardWareConfigDO hardWareConfigDO = hardWareRepository.findByIp(IpUtil.getRemoteAddr());
        if(hardWareConfigDO !=null){
            payParam.setPosNo(hardWareConfigDO.getPosNo());
            payParam.setTid(hardWareConfigDO.getTid());
            payParam.setTxnAmt(payParam.getMoney());
            NumberFormat nf = NumberFormat.getInstance();
            //设置是否使用分组
            nf.setGroupingUsed(false);
            //设置最大整数位数
            nf.setMaximumIntegerDigits(6);
            //设置最小整数位数
            nf.setMinimumIntegerDigits(6);
            payParam.setTraceNo(nf.format(hardWareConfigDO.getTraceNo()));

            hardWareConfigDO.setTraceNo(hardWareConfigDO.getTraceNo()+1);
            hardWareRepository.save(hardWareConfigDO);
        }
        TransactionData transactionData = new TransactionData();
        transactionData.setTranType("F");
        Integer integer = Integer.parseInt(payParam.getTxnAmt());
        transactionData.setTxnAmt((integer*100)+"");
        transactionData.setTraceNo(payParam.getTraceNo());
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long id = idWorker.nextId();
        transactionData.setMerTradeNo(id+"");
        transactionData.setMid(mid);
        transactionData.setTid(payParam.getTid());
        Gson gson = new Gson();
        String transParam = gson.toJson(transactionData);


        String result = restTemplate.doPostBankApi(transParam,"");
        transactionData = gson.fromJson(result,TransactionData.class);
        transactionData.setCardNo(payParam.getCardNo());
        //交易成功回调
        transactionData.setCallBackUrl(callBack);
        transactionData.setCreatDate(new Date());
        transactionData.setIp(IpUtil.getRemoteAddr());
        transactionRepository.save(transactionData);
        if(!"00".equals(transactionData.getRespCode())){
            throw new RuntimeException(gson.toJson(transactionData));
        }
        return transactionData;
    }

    @ApiOperation(httpMethod = "POST",value = "消费订单查询")
    @RequestMapping("query")
    @ResponseApi
    public Object  queryResult(@RequestBody String qrCodeUrl){
        Gson gson = new Gson();
        TransactionData transactionData = payService.queryResult(qrCodeUrl);
        return transactionData;
    }

    @ApiOperation(httpMethod = "POST",value = "主扫交易通知")
    @RequestMapping("notice")
    @SysLog("交易回调")
    public void notice(@RequestBody Notice notice){
        noticeRepository.save(notice);
    }



    @ApiOperation(httpMethod = "POST",value = "充值")
    @RequestMapping("save")
    @SysLog("充值")
    public ResponseData<String> save(@RequestBody String merTradeNo){
        final Object[] objects = new Object[1];
        payService.save(merTradeNo, new ResultListener() {
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
        return (ResponseData<String>) objects[0];
    }

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(httpMethod = "POST",value = "现金充值")
    @RequestMapping("cashSave")
    @ResponseApi
    public Object cashSave(@RequestBody CashDTO cashDTO){
        Gson gson = new Gson();
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        Optional<Patient> optional = patientRepository.findById(cashDTO.getCardNo());
        if(!optional.isPresent()){
            throw new RuntimeException("未获取到患者信息");
        }
        Patient patient = optional.get();
        packageParams.put("cardID", cashDTO.getCardNo());
        packageParams.put("money", cashDTO.getMoney());
        packageParams.put("modeType", "4");
        packageParams.put("operatorid", "0102");
        packageParams.put("patientName", patient.getName());
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long id = idWorker.nextId();
        packageParams.put("serialNumber", id+"");
        packageParams.put("token", token);
        packageParams.put("hosId", hosId);
        String sign = HisMd5Sign.createSign(packageParams, token);
        packageParams.put("sign", sign);
        String param = gson.toJson(packageParams);
        String result = restTemplate.doPostNewHisApi(param,"his/payMedicalCard");
        ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);
        if(responseHisData.getCode()!=0){
            throw new RuntimeException((String) responseHisData.getResponseData());
        }
        Map<String,Object> hisResult = (Map<String, Object>) responseHisData.getResponseData();
        CashVO cashVO = new CashVO();
        cashVO.setHisSerialNumber(MapUtils.getString(hisResult,"hisSerialNumber"));
        cashVO.setBalance(MapUtils.getString(hisResult,"balance"));
        return cashVO;
    }

    @ApiIgnore
    @RequestMapping("hospitalized")
    @SysLog("住院预交金充值")
    public ResponseData<String> saveHospitalized(@RequestBody String tradNo){
        final Object[] objects = new Object[1];
        payService.saveHospitalized(tradNo, new ResultListener() {
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
        return (ResponseData<String>) objects[0];
    }


    @ApiOperation(httpMethod = "POST",value = "住院充值")
    @RequestMapping("saveHos")
    @ResponseApi
    public Object saveHos(@RequestBody String tradNo){
     String result = payService.saveHos(tradNo);
     Gson gson = new Gson();
        ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);

        if(responseHisData.getCode()==1||responseHisData.getCode()==2){
            throw  new RuntimeException((String) responseHisData.getResponseData());
        }
        Map<String,Object> map = new HashMap<>();
        map = (Map<String, Object>) responseHisData.getResponseData();
        PayResultVO payResultVO = new PayResultVO();
        payResultVO.setHisSerialNumber(MapUtils.getString(map,"hisSerialNumber"));
        payResultVO.setHisPayDate(MapUtils.getString(map,"hisPayDate"));
        payResultVO.setBalance(MapUtils.getString(map,"balance"));
        return payResultVO;
    }

    @Autowired
    private HosPatientRepository hosPatientRepository;

    @ApiOperation(httpMethod = "POST",value = "现金住院充值")
    @RequestMapping("cashSaveHos")
    @ResponseApi
    public Object cashSaveHos(@RequestBody CashDTO cashDTO){
        Gson gson = new Gson();
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        HosPatientDO patient = hosPatientRepository.getOne(cashDTO.getCardNo());
        packageParams.put("token", token);
        packageParams.put("hosId", hosId);
        packageParams.put("inHosid", patient.getHosId());
        packageParams.put("patientName", patient.getName());
        packageParams.put("payType", "现金");
        packageParams.put("money", cashDTO.getMoney());
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long id = idWorker.nextId();
        packageParams.put("serialNumber", id+"");

        packageParams.put("payDate", simpleDateFormat.format(new Date()));
        packageParams.put("operatorid", "0102");
        String sign = HisMd5Sign.createSign(packageParams, token);
        packageParams.put("sign", sign);
        String param = gson.toJson(packageParams);
        String result = restTemplate.doPostHisSaveApi(param,"his/paymenPrepaid");
        if(StringUtils.isEmpty(result)){
            throw new RuntimeException("HIS系统无返回值");
        }
        ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);

        if(responseHisData.getCode()==1||responseHisData.getCode()==2){
            throw  new RuntimeException((String) responseHisData.getResponseData());
        }
        Map<String,Object> map = new HashMap<>();
        map = (Map<String, Object>) responseHisData.getResponseData();
        PayResultVO payResultVO = new PayResultVO();
        payResultVO.setHisSerialNumber(MapUtils.getString(map,"hisSerialNumber"));
        payResultVO.setHisPayDate(MapUtils.getString(map,"hisPayDate"));
        payResultVO.setBalance(MapUtils.getString(map,"balance"));
        return payResultVO;
    }


    @ApiOperation(httpMethod = "POST",value = "银行卡充值")
    @RequestMapping("bankSave")
    @ResponseApi
    public Object bankSave(@RequestBody BankDTO bankDTO){
        Gson gson = new Gson();
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        Optional<Patient> optional = patientRepository.findById(bankDTO.getCardNo());
        if(!optional.isPresent()){
            throw new RuntimeException("未获取到患者信息");
        }
        Patient patient = optional.get();
        packageParams.put("cardID", bankDTO.getCardNo());
        packageParams.put("money", bankDTO.getSaveMoney());
        packageParams.put("modeType", "3");
        packageParams.put("operatorid", "0102");
        packageParams.put("patientName", patient.getName());
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long id = idWorker.nextId();
        packageParams.put("serialNumber", id+"");
        packageParams.put("token", token);
        packageParams.put("hosId", hosId);
        String sign = HisMd5Sign.createSign(packageParams, token);
        packageParams.put("sign", sign);
        String param = gson.toJson(packageParams);
        String result = restTemplate.doPostNewHisApi(param,"his/payMedicalCard");
        ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);
        if(responseHisData.getCode()!=0){
            throw new RuntimeException((String) responseHisData.getResponseData());
        }
        Map<String,Object> hisResult = (Map<String, Object>) responseHisData.getResponseData();
        BankVO bankVO = new BankVO();
        bankVO.setHisSerialNumber(MapUtils.getString(hisResult,"hisSerialNumber"));
        bankVO.setBalance(MapUtils.getString(hisResult,"balance"));
        return bankVO;
    }



    @ApiOperation(httpMethod = "POST",value = "住院银行卡充值")
    @RequestMapping("bankSaveHos")
    @ResponseApi
    public Object bankSaveHos(@RequestBody BankDTO bankDTO){
        Gson gson = new Gson();
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        Optional<HosPatientDO> optional = hosPatientRepository.findById(bankDTO.getCardNo());
        if(!optional.isPresent()){
            throw new RuntimeException("未获取到患者信息");
        }
        HosPatientDO patient = optional.get();

        packageParams.put("token", token);
        packageParams.put("hosId", hosId);
        packageParams.put("inHosid", patient.getHosId());
        packageParams.put("patientName", patient.getName());
        packageParams.put("payType", "银联");
        packageParams.put("money", bankDTO.getSaveMoney());
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long id = idWorker.nextId();
        packageParams.put("serialNumber", id+"");

        packageParams.put("payDate", simpleDateFormat.format(new Date()));
        packageParams.put("operatorid", "0102");
        String sign = HisMd5Sign.createSign(packageParams, token);
        packageParams.put("sign", sign);


        String param = gson.toJson(packageParams);
        String result = restTemplate.doPostNewHisApi(param,"his/paymenPrepaid");
        ResponseHisData<Object> responseHisData = gson.fromJson(result,ResponseHisData.class);
        if(responseHisData.getCode()!=0){
            throw new RuntimeException((String) responseHisData.getResponseData());
        }
        Map<String,Object> hisResult = (Map<String, Object>) responseHisData.getResponseData();
        BankVO bankVO = new BankVO();
        bankVO.setHisSerialNumber(MapUtils.getString(hisResult,"hisSerialNumber"));
        bankVO.setBalance(MapUtils.getString(hisResult,"balance"));
        return bankVO;
    }
}
