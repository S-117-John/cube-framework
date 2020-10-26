package com.cube.kiosk.modules.common;

import lombok.Data;

@Data
public class ResultData<T> {

    private int code;

    private T responseData;
}
