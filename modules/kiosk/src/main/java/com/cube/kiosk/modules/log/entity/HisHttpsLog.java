package com.cube.kiosk.modules.log.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "HIS_LOG")
public class HisHttpsLog {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private Date createTime;

    @Column(length = 3000)
    private String param;

    @Column(length = 3000)
    private String result;

    private String note;
}
