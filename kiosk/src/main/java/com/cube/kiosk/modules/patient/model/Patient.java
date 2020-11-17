package com.cube.kiosk.modules.patient.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author LMZ
 */
@Data
public class Patient {

//    @SerializedName("patientname")
    private String name;

//    @SerializedName("sex")
    private String sex;

//    @SerializedName("age")
    private String age;

    /**
     * 卡余额
     */
    private Double balance;

    /**
     * 总充值金额
     */
    private Double amount;

    private String cardNo;
}
