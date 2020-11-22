package com.cube.kiosk.modules.register.model;

import com.cube.kiosk.modules.common.model.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel
public class RegisterParam{

    @NotBlank(message = "卡号不可为空")
    @Length(min=32,message="卡号长度不可小于32")
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @NotBlank(message = "姓名不可为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    @NotBlank(message = "身份证号不可为空")
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "住址")
    private String address;

    @NotBlank(message = "电话不可为空")
    @Pattern(regexp="^((13[0-9])|(15[^4,\\D])|(18[0,3-9]))\\d{8}$", message="手机号格式不正确")
    @ApiModelProperty(value = "电话")
    private String phone;

    @NotBlank(message = "性别不可为空")
    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "生日(yyyy-MM-dd)")
    private String birthday;

}
