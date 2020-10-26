package com.cube.kiosk.modules.hardware.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "HARD_WARE_RECORD")
public class HardWareRecordDO {

    @Id
    @GeneratedValue(
            generator = "system-uuid"
    )
    @GenericGenerator(
            name = "system-uuid",
            strategy = "uuid"
    )
    private String id;

    private Date createTime;

    private String ip;

    private String param;

    private String result;



}
