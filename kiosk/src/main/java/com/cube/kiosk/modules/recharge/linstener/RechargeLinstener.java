package com.cube.kiosk.modules.recharge.linstener;

public interface RechargeLinstener {

    void success(Object object);

    void error(Object object);

    void exception(Object object);
}
