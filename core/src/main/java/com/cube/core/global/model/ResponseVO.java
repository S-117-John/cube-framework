package com.cube.core.global.model;

import lombok.Data;

@Data
public class ResponseVO {

    private String code;

    private String message;

    private Object data;
}
