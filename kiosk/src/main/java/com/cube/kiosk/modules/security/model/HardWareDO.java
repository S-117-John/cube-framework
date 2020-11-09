package com.cube.kiosk.modules.security.model;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "HARDWARE_INFO")
public class HardWareDO {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(unique = true)
    private String ip;

    private String name;

    private String note;

    @Column(nullable = false,unique = true)
    private String posNo;

    @Column(nullable = false)
    private String tid;

    @Column(nullable = false,length = 6)
    private Integer traceNo;
}