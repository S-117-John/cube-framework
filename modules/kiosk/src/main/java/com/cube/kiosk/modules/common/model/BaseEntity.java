package com.cube.kiosk.modules.common.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {

    private Date creatDate;
}
