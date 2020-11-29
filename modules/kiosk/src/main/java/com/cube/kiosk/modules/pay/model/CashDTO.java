package com.cube.kiosk.modules.pay.model;

import lombok.Data;

@Data
public class CashDTO {

    private String cardNo;

    private String money;

    private String moneyCount;
}
