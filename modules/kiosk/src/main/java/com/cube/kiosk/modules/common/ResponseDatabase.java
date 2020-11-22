package com.cube.kiosk.modules.common;

/**
 * @author LMZ
 */
public class ResponseDatabase {

    public static <T> ResponseData<T> newResponseData(){
        return new ResponseData<>();
    }
}
