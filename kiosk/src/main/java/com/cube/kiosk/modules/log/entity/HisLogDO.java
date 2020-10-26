package com.cube.kiosk.modules.log.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "HIS_LOG")
public class HisLogDO {

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

    private String method;

    private String result;
}
