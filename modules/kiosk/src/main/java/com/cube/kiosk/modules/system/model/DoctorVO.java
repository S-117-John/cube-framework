package com.cube.kiosk.modules.system.model;

import com.cube.kiosk.modules.system.entity.DeptDO;
import com.cube.kiosk.modules.system.entity.DoctorDO;
import lombok.Data;

@Data
public class DoctorVO {

    private DoctorDO doctor;

    private DeptDO dept;
}
