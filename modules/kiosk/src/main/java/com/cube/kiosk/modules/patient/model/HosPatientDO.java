package com.cube.kiosk.modules.patient.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HOS_PATIENT")
@Data
public class HosPatientDO {

    @Id
    private String hosId;

    private String name;

    private String sex;

    private String age;

    private String idCard;

}
