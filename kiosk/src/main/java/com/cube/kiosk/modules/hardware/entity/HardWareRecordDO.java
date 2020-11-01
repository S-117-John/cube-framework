package com.cube.kiosk.modules.hardware.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @Column(length = 2000)
    private String result;



}
