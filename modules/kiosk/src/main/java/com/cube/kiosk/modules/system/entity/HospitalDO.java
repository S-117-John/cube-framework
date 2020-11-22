package com.cube.kiosk.modules.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CMS_HOSPITAL")
public class HospitalDO {

    @Id
    private String id;

    private String title;

    @Column(length = 3000)
    private String content;
}
