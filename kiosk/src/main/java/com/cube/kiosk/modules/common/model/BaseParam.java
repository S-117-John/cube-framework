package com.cube.kiosk.modules.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseParam {

    private String hardParam;

    @ApiModelProperty(value = "卡号")
    private String cardNo;
}
