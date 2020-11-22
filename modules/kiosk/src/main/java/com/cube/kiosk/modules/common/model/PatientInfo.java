package com.cube.kiosk.modules.common.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author LMZ
 */
@Data
public class PatientInfo {
    @SerializedName("patientname")
    private String name;

    private String sex;

    private String age;

    private String ageUnit;

    private String birthday;

    private String identitycard;

    private String registeredAddress;//户口地址



    private String sickType;//费用类别

    private String employment;//职业

    private String telephone;//手机号

    private String contactAddress;

    private String postalCode;

    private String workUnit;

    private String unitTelephone;

    private String unitAddress;

    private String unitPostCode;

    private String nation;

    private String nationality;

    private String cardState;

    private String diagnoseId;

    private String cardType;

    private String patientname;//病人姓名

    private Double balance;//卡余额

    private Double amount;//总金额

    private String hisSerialNumber;//His流水号
    private String  cardID;//就诊卡号
    private String  officeName;//住院科室
    private String  enterhosDate;//入院日期
    private String  inhosTimes;//住院次数
    private String  officebedId;//科床号
    private String  doctorName;//主治医师
    private String  cardTypeName;//卡类型名称
    private String  patienttype;
}
