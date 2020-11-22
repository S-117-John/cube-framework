package com.cube.kiosk.modules.pay.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "NOTICE_RECORD")
public class Notice {

    @Id
    @GeneratedValue(
            generator = "system-uuid"
    )
    @GenericGenerator(
            name = "system-uuid",
            strategy = "uuid"
    )
    private String id;

    private String merTradeNo;

    private String orgSysTrace;

    private String bankDate;

    private String bankTime;

    private String oldQrOrderNo;

    private String oldTermId;

    private String oldPayType;

    private String oldBankDate;

    private String oldBankTime;

    private String oldRespCode;

    private String oldRespMsg;

    private String oldTradeId;

    private String oldTradeNo;

    private String respCode;

    private String respMsg;

}
