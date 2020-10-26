package com.cube.kiosk.modules.patient.service;

import com.cube.kiosk.modules.common.model.ResultListener;

/**
 * @author LMZ
 */
public interface PatientService {

    /**
     * 获取病人信息
     * @param id 卡号
     * @return
     */
    void get(String id, ResultListener linstener);
}
