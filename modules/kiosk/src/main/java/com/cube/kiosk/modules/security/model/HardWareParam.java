package com.cube.kiosk.modules.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@ApiModel("自助机注册信息")
@Data
public class HardWareParam {


    @ApiModelProperty(value = "自助机ip",required = true)
    @Column(nullable = false)
    private String ip;

    @ApiModelProperty(value = "自助机名称",required = true)
    @Column(nullable = false)
    private String name;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty(value = "设备唯一编号",required = true)
    @Column(nullable = false)
    private String posNo;

    @ApiModelProperty(value = "终端号",required = true)
    @Column(nullable = false)
    private String tid;

    @ApiModelProperty(value = "终端流水号（初始写1）",required = true)
    @Column(nullable = false)
    private Integer traceNo;
}
