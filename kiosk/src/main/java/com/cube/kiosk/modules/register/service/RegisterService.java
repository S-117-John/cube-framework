package com.cube.kiosk.modules.register.service;

import com.cube.kiosk.modules.common.model.ResultListener;
import com.cube.kiosk.modules.register.model.RegisterParam;

public interface RegisterService {

    void register(RegisterParam param, ResultListener listener);
}
