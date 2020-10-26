package com.cube.kiosk.modules.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("自助机注册信息")
@Data
public class HardWareParam {


    @ApiModelProperty("自助机ip")
    private String ip;

    @ApiModelProperty("自助机名称")
    private String name;

    @ApiModelProperty("备注")
    private String note;
}
