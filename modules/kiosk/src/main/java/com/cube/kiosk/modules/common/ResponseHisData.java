package com.cube.kiosk.modules.common;

import lombok.Data;

@Data
public class ResponseHisData<T> {

    private int code;

    private T responseData;
}
