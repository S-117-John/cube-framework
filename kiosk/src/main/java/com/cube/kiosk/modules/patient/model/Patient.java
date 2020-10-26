package com.cube.kiosk.modules.patient.model;

import lombok.Data;

/**
 * @author LMZ
 */
@Data
public class Patient {

    private String name;

    private String sex;

    private String age;

    /**
     * 卡余额
     */
    private Double balance;

    /**
     * 总充值金额
     */
    private Double amount;
}
