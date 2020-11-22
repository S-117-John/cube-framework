package com.cube.kiosk.modules.common.model;

/**
 * @author LMZ
 */
public interface ResultListener {

    void success(Object object);

    void error(Object object);

    void exception(Object object);
}
