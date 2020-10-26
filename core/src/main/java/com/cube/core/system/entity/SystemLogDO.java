package com.cube.core.system.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "SYSTEM_LOG")
@Data
public class SystemLogDO {

    @Id
    @GeneratedValue(
            generator = "system-uuid"
    )
    @GenericGenerator(
            name = "system-uuid",
            strategy = "uuid"
    )
    private String id;
    private String username;
    private String operation;
    private String method;
    private String params;
    private String ip;
    private String result;
    private Date createDate;
}
