package com.cube.kiosk.modules.register.model;

import com.cube.kiosk.modules.common.model.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class RegisterParam extends BaseParam {

    @ApiModelProperty(name = "姓名")
    private String name;

    @ApiModelProperty(name = "身份证号")
    private String idCard;

    @ApiModelProperty(name = "住址")
    private String address;

    @ApiModelProperty(name = "电话")
    private String phone;

    @ApiModelProperty(name = "性别")
    private String sex;

    @ApiModelProperty(name = "生日(yyyy-MM-dd)")
    private String birthday;

}
