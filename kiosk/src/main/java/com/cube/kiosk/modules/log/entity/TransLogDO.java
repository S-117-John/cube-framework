package com.cube.kiosk.modules.log.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRANS_LOG")
@Data
public class TransLogDO {

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

    @Column(length = 3000)
    private String param;

    private String method;

    @Column(length = 3000)
    private String result;
}
