package com.cube.kiosk.modules.common.model;

import lombok.Data;

@Data
public class RequestData<T> {

    private String code;

    private String message;

    private T data;

}
