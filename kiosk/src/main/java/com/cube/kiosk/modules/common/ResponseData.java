package com.cube.kiosk.modules.common;

import lombok.Data;

/**
 * @author LMZ
 */
@Data
public class ResponseData<T> {

    private int code;

    private String message;

    private T data;

    private String type;


}
